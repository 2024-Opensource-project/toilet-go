import requests
from datetime import date, timedelta
from bs4 import BeautifulSoup
from dto import HouseDTO
from typing import List


def get_house_list() -> List[BeautifulSoup]:
    url = "https://apply.gh.or.kr/sb/sr/sr7150/selectPbancRentHouseList.do"
    body = {
        'searchArea': '',
        'searchCate': '01', #행복주택
        'searchState': '', #모집중인거 보려면 1 아니면 빈값
        'searchTitle': '',
        'previewYn': '',
        'pbancNo': '',
        'pbancKndCd': '',
        'bizTyNm': '',
        'pageIndex': '',
        'gvPgmId': 'SR7150M00'
    }

    rs = requests.post(url, data=body).text
    #rs = open("./gh_list_html.txt", mode="r", encoding="utf-8")

    soup = BeautifulSoup(rs, "html.parser")
    list = soup.find_all("a", "text_cut")
    return list


def get_house_info(data: BeautifulSoup) -> List[HouseDTO]:
    url = "https://apply.gh.or.kr/sb/sr/sr7150/selectPbancDetailView.do"
    body = {
        'searchArea': ',',
        'searchCate': '01', #행복주택
        'searchState': '', #모집중인거 보려면 1 아니면 빈값
        'searchTitle': '',
        'previewYn': 'N',
        'pbancNo': data["data-pbancno"],
        'pbancKndCd': data["data-pbanckndcd"],
        'bizTyNm': '행복주택',
        'pageIndex': '1',
        'gvPgmId': 'SR7150M00'
    }

    rs = requests.post(url, data=body).text
    #rs = open("./gh_house_info.txt", mode="r", encoding="utf-8").read()
    soup = BeautifulSoup(rs, "html.parser")

    house_list = soup.find_all("table", "mt20")
    submissionDate = soup.find_all("ul", "bul_list mt10")[1].text.strip()
    index = 0

    houses_info: List[HouseDTO] = []
    for house_info in house_list:
        data_list = house_info.find_all("td", "txt_l")
        house = HouseDTO(
            name = data_list[0].text.strip(),
            address = data_list[1].text.strip(),
            count = data_list[3].text.strip(),
            movingDate = data_list[5].text.strip(),
            submissionDate = submissionDate,
            description=str(soup.find("div", "tbl_scroll mt10"))
        )
        houses_info.append(house)
        index += 1
    return houses_info

import requests
from datetime import date, timedelta
from bs4 import BeautifulSoup
from dto import HouseDTO, HouseDetailDTO
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

    house_info = soup.find("table", "mt20")
    applyDate = soup.find_all("ul", "bul_list mt10")[1].text
    data_list = house_info.find_all("td", "txt_l")
    house = HouseDTO(
        name = data_list[0].text.strip(),
        address = data_list[1].text.strip(),
        moveInDate = data_list[5].text.strip(),
        applyStartDate = applyDate[11:21],
        applyEndDate = applyDate[30:40]
    )

    house_detail_list = soup.find_all("tr", "tb_house_con")
    houseDetail_info : List[HouseDetailDTO] = []
    for text in house_detail_list:
        detail_data = text.find_all("td")
        house_detail = HouseDetailDTO(
            type = detail_data[0].text.strip(),
            size = detail_data[1].text.strip(),
            supplyCount = detail_data[2].text.strip(),
            deposit = detail_data[3].text.strip(),
            monthlyRent = detail_data[4].text.strip()
        )
        houseDetail_info.append(house_detail)

    house.houseDetails = houseDetail_info
    return house

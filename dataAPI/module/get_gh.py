import requests
from datetime import date, timedelta
from bs4 import BeautifulSoup
from module.dto import HouseDTO, HouseDetailDTO
from typing import List
import urllib.parse


def get_house_list(num) -> List[BeautifulSoup]:
    url = "https://apply.gh.or.kr/sb/sr/sr7150/selectPbancRentHouseList.do"
    body = {
        'searchArea': '',
        'searchCate': '01', #행복주택
        'searchState': '1', #모집중인거 보려면 1 아니면 빈값
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
    return list[:num-1]


def get_house_info(data: BeautifulSoup) -> List[HouseDTO]:
    url = "https://apply.gh.or.kr/sb/sr/sr7150/selectPbancDetailView.do"
    body = {
        'searchArea': '',
        'searchCate': '01', #행복주택
        'searchState': '1', #모집중인거 보려면 1 아니면 빈값
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
    house_detail_list = soup.find_all("tbody", "tb_house")
    index=0

    houses_info: List[HouseDTO] = []
    for house in house_list:
        data_list = house.find_all("td", "txt_l")
        apply_date = soup.find_all("ul", "bul_list mt10")[index*2+1].text
        house = HouseDTO(
            name = data_list[0].text.strip(),
            address = data_list[1].find("span").text.strip(),
            moveInDate = data_list[5].text.strip(),
            applyStartDate = apply_date[11:21],
            applyEndDate = apply_date[30:40],
            company = 'GH',
            redirectUrlForm = urllib.parse.urlencode(body)
        )

        house_details: List[HouseDetailDTO] = []
        for detail in house_detail_list[index].find_all("tr", "tb_house_con"):
            detail_data = detail.find_all("td")
            house_detail = HouseDetailDTO(
                type = detail_data[0].text.strip(),
                size = detail_data[1].text.strip(),
                supplyCount = detail_data[2].text.strip(),
                deposit = detail_data[3].text.strip(),
                monthlyRent = detail_data[4].text.strip()
            )
            house_details.append(house_detail)
        house.houseDetails = house_details
        houses_info.append(house)
        index += 1
    return houses_info

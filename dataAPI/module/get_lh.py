import requests
from datetime import date, timedelta
from bs4 import BeautifulSoup
from module.dto import HouseDTO, HouseDetailDTO
from typing import List
import urllib.parse

def get_house_list(days) -> List[BeautifulSoup]:
    today = date.today()
    search_len = timedelta(days=days)
    url = "https://apply.lh.or.kr/lhapply/apply/wt/wrtanc/selectWrtancList.do"
    body = {
        'panId': '', 'ccrCnntSysDsCd': '', 'srchUppAisTpCd': '06', 'uppAisTpCd': '06', 'aisTpCd': '10', 'srchAisTpCd': '10',
        'prevListCo': '', 'mi': '1026', 'currPage': '1', 'srchY': 'Y', 'indVal': 'N', 'viewType': '',
        'srchFilter': 'N', 'mvinQf': '0', 'cnpCd': '41', 'panSs': '공고중', 'schTy': '0',
        'startDt': str(today - search_len), 'endDt': str(today), 'panNm': '', 'listCo': '50'
    }

    rs = requests.post(url, data=body).text
    #rs = open("./html_text.txt", mode="r", encoding="utf-8")

    soup = BeautifulSoup(rs, "html.parser")
    list = soup.find_all("a", "wrtancInfoBtn")
    return list

def get_house_info(data: BeautifulSoup) -> List[HouseDTO]:
    url = "https://apply.lh.or.kr/lhapply/apply/wt/wrtanc/selectWrtancInfo.do"
    body = {
        'panId': data["data-id1"], 'ccrCnntSysDsCd': data["data-id2"], 'srchUppAisTpCd': data["data-id3"],
        'uppAisTpCd': data["data-id3"], 'aisTpCd': data["data-id4"], 'srchAisTpCd': data["data-id4"],
        'prevListCo': '', 'mi': '1026', 'currPage': '1', 'srchY': 'N', 'indVal': '', 'viewType': '', 'srchFilter': 'N',
        'mvinQf': '0', 'cnpCd': '41', 'panSs': '공고중', 'schTy': '0', 'startDt': '2024-04-18', 'endDt': '2024-06-18', 'panNm': '', 'listCo': '50'
    }

    rs = requests.post(url, data=body).text
    #rs = open("./house_info.txt", mode="r", encoding="utf-8").read()
    soup = BeautifulSoup(rs, "html.parser")

    house_list = soup.find_all("ul", "list_st1 li_w25")
    house_detail_list = soup.find_all("tbody")
    index = 0

    name_list = []
    for i in range(10):  # 예를 들어 0부터 9까지 ID가 있는 경우를 가정
        div_id = f"wrtancSbdTab{i}"
        div_element = soup.find(id=div_id)
        if div_element:  # 해당 ID가 존재하는 경우에만 진행
            titles = div_element.find('h4', class_='tit2')
            for title in titles:
                name_list.append(title.get_text())

    houses_info: List[HouseDTO] = []
    for house in house_list:
        apply_date = soup.find("label", id="sta_acpDt").text.split("~")
        house = HouseDTO(
            name = name_list[index].strip(),
            address = house.find("li", "w100").text.replace("소재지 :", "").strip(),
            moveInDate = house.find_all("li")[-1].text.replace("입주예정월 : ", "").strip(),
            applyStartDate = apply_date[0].strip(),
            applyEndDate = apply_date[1].strip(),
            company = 'LH',
            redirectUrlForm = urllib.parse.urlencode(body)
        )

        house_details: List[HouseDetailDTO] = []
        for detail in house_detail_list[index].find_all("tr"):
            data = detail.find_all("td")
            house_detail = HouseDetailDTO(
                type = detail.find("th").text.strip(),
                size = data[0].text.strip(),
                supplyCount = data[1].text.strip(),
                deposit = data[2].text.strip(),
                monthlyRent = data[3].text.strip()
            )
            house_details.append(house_detail)
        house.houseDetails = house_details
        houses_info.append(house)
        index += 1
    return houses_info

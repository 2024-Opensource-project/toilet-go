import requests
from datetime import date, timedelta
from bs4 import BeautifulSoup
from dto import HouseDTO
from typing import List

def get_house_list(days: int) -> List[BeautifulSoup]:
    today = date.today()
    search_len = timedelta(days=days)
    url = "https://apply.lh.or.kr/lhapply/apply/wt/wrtanc/selectWrtancList.do"
    body = {
        'panId': '', 'ccrCnntSysDsCd': '', 'srchUppAisTpCd': '06', 'uppAisTpCd': '06', 'aisTpCd': '10', 'srchAisTpCd': '10',
        'prevListCo': '', 'mi': '1026', 'currPage': '1', 'srchY': 'Y', 'indVal': 'N', 'viewType': '',
        'srchFilter': 'N', 'mvinQf': '0', 'cnpCd': '41', 'panSs': '', 'schTy': '0',
        'startDt': str(today - search_len), 'endDt': str(today), 'panNm': '', 'listCo': '50'
    }

    # rs = requests.post(url, data=body).text
    rs = open("./html_text.txt", mode="r", encoding="utf-8")

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

    # rs = requests.post(url, data=body).text
    rs = open("./house_info.txt", mode="r", encoding="utf-8").read()
    soup = BeautifulSoup(rs, "html.parser")

    address_list = soup.find_all("li", "w100")[:-1]  # 주소들
    index = 0

    houses_info: List[HouseDTO] = []
    for address in address_list:
        house = HouseDTO(
            address = address.text.strip(),
            submissionDate=soup.find("label", id="sta_acpDt").text.strip() if soup.find("label", id="sta_acpDt") else None,  # 신청일
            name=soup.find("a", id="tagA" + str(index)).text.strip() if soup.find("a", id="tagA" + str(index)) else None,  # 이름 / A0, A1... 증가
            description=str(soup.find("div", "tbl_st tbl_rsp tyCross tymBtn tblmobToge")) if soup.find("div", "tbl_st tbl_rsp tyCross tymBtn tblmobToge") else None  # 설명
        )
        houses_info.append(house)
        index += 1
    return houses_info

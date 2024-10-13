from module import get_lh
from module import get_gh
from module.local_server import Local
from module.db_house import House

local = Local()
house = House()

def upload_lh(day=30):
    all_list = get_lh.get_house_list(day)  #기본 30일, 최근부터 n일 전까지 숫자로 설정가능. ex) 30 - 한달치
    for list in all_list:
        if not house.isAlreadyAdded('LH', list["data-id1"]):
            list_of_house = get_lh.get_house_info(list)
            local.upload_houses(list_of_house)
            house.addHousePostId('LH', list["data-id1"])


def upload_gh(num=10):
    gh_all_list = get_gh.get_house_list(num)  #기본 10개, 숫자넣으면 한페이지 내에 최대 몇갠지 설정가능. ex) 5 - 최근부터 5개
    for list in gh_all_list:
        if not house.isAlreadyAdded('GH', list["data-pbancno"]):
            list_of_house = get_gh.get_house_info(list)
            local.upload_houses(list_of_house)
            house.addHousePostId('GH', list["data-pbancno"])

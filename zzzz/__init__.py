import getLH
import get_gh
from local_server import Local

local = Local()

def uplooad_lh():
    all_list = getLH.get_house_list() #기본 30일, 최근부터 n일 전까지 숫자로 설정가능. ex) 30 - 한달치
    for list in all_list:
        list_of_house = getLH.get_house_info(list)
        local.upload_houses(list_of_house)

def upload_gh():
    gh_all_list = get_gh.get_house_list() #기본 10개, 숫자넣으면 한페이지 내에 최대 몇갠지 설정가능. ex) 5 - 최근부터 5개
    for list in gh_all_list:
        list_of_house = get_gh.get_house_info(list)
        local.upload_houses(list_of_house)

uplooad_lh()
upload_gh()


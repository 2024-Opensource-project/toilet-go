import getLH
import get_gh
from local_server import Local

local = Local()

def uplooad_lh():
    all_list = getLH.get_house_list(30)
    for list in all_list:
        list_of_house = getLH.get_house_info(list)
        print(local.upload_houses(list_of_house))

def upload_gh():
    gh_all_list = get_gh.get_house_list()[:5]
    for list in gh_all_list:
        list_of_house = get_gh.get_house_info(list)
        print(local.upload_houses(list_of_house))

uplooad_lh()
upload_gh()
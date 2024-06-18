import getLH
import requests
import json
from dataclasses import asdict

list = getLH.get_house_list(30)
list_of_house = getLH.get_house_info(list[0])

url = "http://localhost:8080/house/add"

for house in list_of_house:
    body = asdict(house)
    rs = requests.post(url, json=body)
    print(body)
    print(rs)

#for house in list:
#    getLH.get_house_info(house)
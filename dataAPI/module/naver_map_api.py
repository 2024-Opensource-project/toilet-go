# save this as naver_map_api.py
import sys
import requests

class NaverMapAPI:
    def __init__(self):
        self.url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode"
        self.secret_id = "1vq6oly1eb"
        self.secret_pw = "JK0g289n3e7y28SGSV3ZC3oDs0iSJpSCsPvdbj4t"

    def get_lat_lon(self, address):
        params = {
            "query": address
        }
        headers = {
            "X-NCP-APIGW-API-KEY-ID": self.secret_id,
            "X-NCP-APIGW-API-KEY": self.secret_pw
        }
        response = requests.get(url=self.url, headers=headers, params=params)
        response_json = response.json()

        if response_json["meta"]["totalCount"] == 0:
            return None, None
        else:
            location_x = response_json["addresses"][0]["x"]
            location_y = response_json["addresses"][0]["y"]
            return location_y, location_x

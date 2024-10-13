import requests
from module.config import local
from dataclasses import asdict



class Local:
    rq = requests.Session()
    admin_id = local.id
    admin_pw = local.pw
    server_url = local.server
    login_url = local.login_url
    upload_url = local.upload_url

    def get_admin_session(self):
        id = self.admin_id
        pw = self.admin_pw
        url = self.server_url + self.login_url
        body = {"userId": id, "password": pw}
        self.rq.post(url=url,  data=body)

    def upload_houses(self, house_list):
        self.get_admin_session()
        url = self.server_url + self.upload_url
        for house in house_list:
            #print(house)
            body = asdict(house)
            ps = self.rq.post(url, json=body)
            #print(ps.text)
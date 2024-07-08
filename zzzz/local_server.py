import requests
import json
from dataclasses import asdict


class Local:
    server_url = "http://localhost:8080"
    rq = requests.Session()

    def __init__(self):
        self.get_session()

    def get_session(self):
        id = "admin"
        pw = "test"
        url = self.server_url + "/signin"
        body = {"id": id, "pw": pw}
        self.rq.post(url=url, data=body)

    def upload_houses(self, house):
        url = self.server_url + "/house/add"
        body = asdict(house)
        ps = self.rq.post(url, json=body)
        status = ps.status_code
        if status != 200:
            return "Error"
        return "success!"

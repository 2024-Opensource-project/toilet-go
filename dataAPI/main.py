from flask import Flask, render_template, request, redirect
from module.upload_house_info import upload_gh, upload_lh
from module.naver_map_api import NaverMapAPI
import os

app = Flask(__name__)

class MyServer:
    def __init__(self):
        self.app = Flask(__name__)
        self.host = '0.0.0.0'
        self.port = int(os.getenv('SERVER_PORT'))
        self.upload_lh_num = 30 #default 30
        self.upload_gh_num = 10 #default 10
        self.map_api = NaverMapAPI()
        self._set_routes()

    def _set_routes(self):
        @self.app.route("/upload", methods=['POST'])
        def upload():
            try:
                upload_lh(self.upload_lh_num)
                upload_gh(self.upload_gh_num)
            except Exception as e:
                return e
            return "success"

        @self.app.route("/set", methods=['GET'])
        def set_num_page():
            return render_template('setting.html', lh=self.upload_lh_num, gh=self.upload_gh_num)

        @self.app.route("/set", methods=['POST'])
        def set_num():
            lh_input = request.form.get('lh')
            gh_input = request.form.get('gh')

            if lh_input:
                self.upload_lh_num = int(lh_input)

            if gh_input:
                self.upload_gh_num = int(gh_input)
            return redirect('/set')

        @self.app.route("/latandlng", methods=['GET'])
        def lat_lng_page():
            return render_template('lat_lng.html')

        @self.app.route("/latandlng", methods=['POST'])
        def get_lat_lng():
            address = request.form.get('address')
            latitude, longitude = self.map_api.get_lat_lon(address)
            if latitude is None:
                return "Not Found"
            else:
                return f"{latitude},{longitude}"

    def run(self):
        self.app.run(host=self.host, port=self.port)

if __name__ == '__main__':
    app_instance = MyServer()
    app_instance.run()
from flask import Flask, render_template, request
from module.naver_map_api import NaverMapAPI
import os

app = Flask(__name__)


class MyServer:
    def __init__(self):
        self.app = Flask(__name__)
        self.host = '0.0.0.0'
        self.port = int(os.getenv('SERVER_PORT', 54090))
        self.map_api = NaverMapAPI()
        self._set_routes()

    def _set_routes(self):
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

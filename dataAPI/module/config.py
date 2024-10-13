import os
class db:
    server = os.getenv('DB_SERVER')
    id = os.getenv('DB_USERNAME')
    pw = os.getenv('DB_PASSWORD')
    name = os.getenv('DB_NAME')


class local:
    server = os.getenv('LOCAL_SERVER')
    id = os.getenv('LOCAL_ID')
    pw = os.getenv('LOCAL_PASSWORD')
    login_url = os.getenv('LOCAL_LOGIN_URL')
    upload_url = os.getenv('LOCAL_UPLOAD_URL')

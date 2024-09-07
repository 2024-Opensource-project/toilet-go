import pymysql
from module.config import db

class House:

    def __init__(self):
        self.conn = pymysql.connect(host=db.server,
                                    user=db.id, 
                                    passwd=db.pw, 
                                    db=db.name, 
                                    ssl={'ssl': False})
        self.cur = self.conn.cursor()

    def isAlreadyAdded(self, name, num):
        sql = f"SELECT 1 FROM house WHERE name = %s AND apply_post_id = %s"
        self.cur.execute(sql, (name, num))
        result = self.cur.fetchone()
        if result is None:
            return False
        return True

    def addHousePostId(self, name, num):
        sql = f"INSERT INTO house(name, apply_post_id) VALUES(%s, %s)"
        self.cur.execute(sql, (name, num))
        self.conn.commit()
        return

    def close(self):
        self.cur.close()
        self.conn.close()

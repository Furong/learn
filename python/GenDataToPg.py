import psycopg2
import random as r
import time

def randomName():
    a1=['张','金','李','王','赵','孙','王']
    a2=['玉','明','龙','芳','军','玲','雨','桐']
    a3=['','立','玲','','国','','杰','雨']
    name=r.choice(a1)+r.choice(a2)+r.choice(a3)
    return name

def genStudentId(index):
    if index < 10:
        return '000'+str(index)
    elif index < 100:
        return '00'+str(index)
    elif index <1000:
        return '0'+str(index)
    else:
        return str(index)

def conDb():
    conn = psycopg2.connect(database='db', user='postgres', password='root', host='127.0.0.1')
    return conn
def closeDb(conn):
    conn.close()

def genDate():
    a1 = (2009, 1, 1, 0, 0, 0, 0, 0, 0)  # 设置开始日期时间元组（1976-01-01 00：00：00）
    a2 = (2012, 12, 31, 23, 59, 59, 0, 0, 0)  # 设置结束日期时间元组（1990-12-31 23：59：59）

    start = time.mktime(a1)  # 生成开始时间戳
    end = time.mktime(a2)  # 生成结束时间戳
    t = r.randint(start, end)  # 在开始和结束时间戳中随机取出一个
    date_touple = time.localtime(t)  # 将时间戳生成时间元组
    date = time.strftime("%Y-%m-%d", date_touple)  # 将时间元组转成格式化字符串（1976-05-21）
    return date



if __name__=='__main__':
    conn = conDb()
    cur = conn.cursor()
    ##批量插入数据
    for index in range(1000,9000):
        name = randomName()
        gender = index % 2
        birth_date = genDate()
        student_id = genStudentId(index)
        print(student_id)
        cur.execute("INSERT INTO t_user (id,name, gender, birth_date,student_id)  VALUES (%s, %s,%s,%s,%s)",
                       (index, name,gender,birth_date,student_id))
    conn.commit()
    conn.close()



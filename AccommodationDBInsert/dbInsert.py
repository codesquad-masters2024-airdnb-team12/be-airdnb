import mysql.connector
import configparser
import pandas as pd
import random
from datetime import datetime, timedelta

# Load configuration
config = configparser.ConfigParser()
with open("config.ini", 'r', encoding='utf-8') as file:
    config.read_file(file)

HOST = config['dbInsert']['HOST']
PORT = int(config['dbInsert']['PORT'])
USER = config['dbInsert']['USER']
PWD = config['dbInsert']['PWD']
DB = config['dbInsert']['DB']


def dbInsert(excel_file_path):
    # 엑셀 파일 읽기
    data = pd.read_excel(excel_file_path)

    # 랜덤 데이터 생성
    data['accommodation_type'] = ['HOTEL' if random.random() < 0.5 else 'HOUSE' for _ in range(len(data))]
    data['country'] = ['KR' for _ in range(len(data))]
    data['bath'] = [random.randint(1, 20) for _ in range(len(data))]
    data['bed'] = [random.randint(1, 20) for _ in range(len(data))]
    data['bedroom'] = [random.randint(1, 20) for _ in range(len(data))]
    data['building_type'] = ['ALL' if random.random() < 0.2 else 'ROOM' for _ in range(len(data))]
    data['cost_per_night'] = [random.randint(100, 100000) * 100 for _ in range(len(data))]
    created_at_values = [datetime.now() - timedelta(days=random.randint(1, 365)) for _ in range(len(data))]
    data['created_at'] = created_at_values
    data['modified_at'] = created_at_values  # created_at 값을 그대로 사용
    data['host_id'] = [random.randint(1, 10) for _ in range(len(data))]
    data['initial_discount_applied'] = [random.randint(0, 1) for _ in range(len(data))]
    data['initial_discount_cnt'] = data['initial_discount_applied'].apply(lambda x: 3 if x == 1 else 0)
    data['max_guests'] = [random.randint(1, 20) for _ in range(len(data))]
    data['monthly_discount_applied'] = [random.randint(0, 1) for _ in range(len(data))]
    data['weekly_discount_applied'] = [random.randint(0, 1) for _ in range(len(data))]

    # coordinate 값 생성 (latitude와 longitude를 결합하여 'POINT(longitude latitude)' 형태로 생성)
    data['coordinate'] = "POINT(" + data['위도'].astype(str) + " " + data['경도'].astype(str) + ")"

    # '상세정보' 열에서 '객실명' 추출
    room_info = data['상세정보'].str.extract(r'객실명:(.*?)객실크기:').fillna('')
    # 새로운 열에 추출한 값 할당
    data['name'] = data['명칭'] + ' ' + room_info[0]

    # 필요없는 위도, 경도 열 제거
    data.drop(columns=['위도', '경도'], inplace=True)

    # 주소
    data['detailed_address'] = data['명칭']
    data['zipcode'] = data['우편번호']
    data['address'] = data['주소']

    data['description'] = data['개요']

    # 필요한 열만 선택
    data = data[['accommodation_type', 'country', 'bath', 'bed', 'bedroom', 'building_type', 'cost_per_night',
                 'created_at', 'host_id', 'initial_discount_applied', 'initial_discount_cnt', 'max_guests', 'modified_at',
                 'monthly_discount_applied', 'weekly_discount_applied', 'coordinate', 'address', 'detailed_address',
                 'zipcode', 'description', 'name']]

    # MySQL과 커넥션 수행
    connection = mysql.connector.connect(
        host=HOST,
        port=PORT,
        user=USER,
        password=PWD,
        db=DB,
        charset='utf8mb4'
    )

    try:
        with connection.cursor() as cursor:
            # facility 테이블에서 facility_id를 가져오기
            cursor.execute("SELECT facility_id FROM facility")
            facilities = cursor.fetchall()
            facility_ids = [facility[0] for facility in facilities]

            # SQL INSERT 문 작성
            sql_accommodation = """INSERT INTO accommodation (accommodation_type, country, bath, bed, bedroom, 
            building_type, cost_per_night, created_at, host_id, initial_discount_applied, initial_discount_cnt, 
            max_guests, modified_at, monthly_discount_applied, weekly_discount_applied, coordinate, address, 
            detailed_address, zipcode, description, name) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, 
            %s, %s, ST_GeomFromText(%s, 4326), %s, %s, %s, %s, %s) """

            sql_facility = """
                INSERT INTO accommodation_facility
                    (accommodation_id, facility_id)
                VALUES (%s, %s)
            """

            # 데이터 삽입
            for _, row in data.iterrows():
                cursor.execute(sql_accommodation, (
                    row['accommodation_type'], row['country'], row['bath'], row['bed'], row['bedroom'],
                    row['building_type'], row['cost_per_night'], row['created_at'], row['host_id'],
                    row['initial_discount_applied'], row['initial_discount_cnt'], row['max_guests'], row['modified_at'],
                    row['monthly_discount_applied'], row['weekly_discount_applied'], row['coordinate'],
                    row['address'], row['detailed_address'], row['zipcode'],
                    row['description'], row['name']
                ))

                # 새로 추가된 숙소 아이디
                accommodation_id = cursor.lastrowid

                # 랜덤으로 최소 3개의 facility_id 선택하여 accommodation_facility에 삽입
                random_facility_ids = random.sample(facility_ids, k=random.randint(3, len(facilities)))
                for facility_id in random_facility_ids:
                    cursor.execute(sql_facility, (accommodation_id, facility_id))

            # 변경사항 커밋
            connection.commit()

    finally:
        connection.close()


# 엑셀 파일 경로 리스트
excel_files = [
    'accommodation1.xls',
    'accommodation2.xls',
    'accommodation5.xls'
]

# 각 엑셀 파일에 대해 dbInsert 함수 호출
for file in excel_files:
    dbInsert("./xls/" + file)


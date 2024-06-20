-- member
INSERT INTO member (LOGIN_TYPE, REFRESH_TOKEN, NAME, IMG_URL, PASSWORD, EMAIL,
                    ACCOUNT_NUMBER, MODIFIED_AT, DELETED, CREATED_AT, ROLE)
VALUES
    ('DEFAULT', 'sample_refresh_token', '박은형', 'https://example.com/image.jpg', 'encoded_password_example', 'parkeh@example.com',
     NULL, '2024-05-10 12:34:56', NULL, '2024-05-10 12:34:56', 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '안종혁', 'https://example.com/image.jpg', 'encoded_password_example', 'ahnjh@example.com',
     NULL, '2024-11-12 12:34:56', NULL, '2024-11-12 12:34:56', 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '정연호', 'https://example.com/image.jpg', 'encoded_password_example', 'jeongyh@example.com',
     NULL, '2022-06-28 12:34:56', NULL, '2022-06-28 12:34:56', 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '박민서', 'https://example.com/image.jpg', 'encoded_password_example', 'parkms@example.com',
     NULL, '2021-04-09 12:34:56', NULL, '2021-04-09 12:34:56', 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '유수현', 'https://example.com/image.jpg', 'encoded_password_example', 'yoosh@example.com',
     NULL, '2023-08-10 12:34:56', NULL, '2023-08-10 12:34:56', 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '김은선', 'https://example.com/image.jpg', 'encoded_password_example', 'kimes@example.com',
     NULL, '2020-12-17 12:34:56', NULL, '2020-12-17 12:34:56', 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '최호빈', 'https://example.com/image.jpg', 'encoded_password_example', 'choihb@example.com',
     NULL, '2018-03-10 12:34:56', NULL, '2018-03-10 12:34:56', 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '김보겸', 'https://example.com/image.jpg', 'encoded_password_example', 'kimbk@example.com',
     NULL, '2019-09-25 12:34:56', NULL, '2019-09-25 12:34:56', 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '곽태영', 'https://example.com/image.jpg', 'encoded_password_example', 'kwakty@example.com',
     NULL, '2020-10-30 12:34:56', NULL, '2020-10-30 12:34:56', 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '서정식', 'https://example.com/image.jpg', 'encoded_password_example', 'seojs@example.com',
     NULL, '2024-02-19 12:34:56', NULL, '2024-02-19 12:34:56', 'GUEST');

-- facility
INSERT INTO facility (NAME, TYPE, CREATED_AT, MODIFIED_AT, FOR_SEARCH) VALUES
    ('무선 인터넷', 'INTERNET_ELECTRONICS', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('TV', 'INTERNET_ELECTRONICS', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('주방', 'ACCOMMODATION', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('세탁기', 'ACCOMMODATION', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('건조기', 'ACCOMMODATION', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('헤어드라이기', 'ACCOMMODATION', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('건물 내 무료 주차', 'ACCESSIBILITY', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('건물 부지 내 유료 주차', 'ACCESSIBILITY', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('에어컨', 'ACCOMMODATION', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('난방', 'ACCOMMODATION', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('수영장', 'ACTIVITY_LEISURE', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('아기 침대', 'KID', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('헬스장', 'ACTIVITY_LEISURE', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('바비큐 그릴', 'FOOD_DRINK', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('아침식사', 'FOOD_DRINK', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('화재경보기', 'CLEAN_SAFETY', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('구급 상자', 'CLEAN_SAFETY', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('소화기', 'CLEAN_SAFETY', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1),
    ('일산화탄소 경보기', 'CLEAN_SAFETY', '2024-06-10 12:34:56', '2024-06-10 12:34:56', 1)
    ;

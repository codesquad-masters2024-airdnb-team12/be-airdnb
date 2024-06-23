-- member
INSERT INTO member (LOGIN_TYPE, REFRESH_TOKEN, NAME, IMG_URL, PASSWORD, EMAIL,
                    ACCOUNT_NUMBER, MODIFIED_AT, DELETED, CREATED_AT, ROLE)
VALUES
    ('DEFAULT', 'sample_refresh_token', '박은형', 'https://example.com/image.jpg', 'encoded_password_example', 'parkeh@example.com',
     NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '안종혁', 'https://example.com/image.jpg', 'encoded_password_example', 'ahnjh@example.com',
     NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '정연호', 'https://example.com/image.jpg', 'encoded_password_example', 'jeongyh@example.com',
     NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '박민서', 'https://example.com/image.jpg', 'encoded_password_example', 'parkms@example.com',
     NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '유수현', 'https://example.com/image.jpg', 'encoded_password_example', 'yoosh@example.com',
     NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '김은선', 'https://example.com/image.jpg', 'encoded_password_example', 'kimes@example.com',
     NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '최호빈', 'https://example.com/image.jpg', 'encoded_password_example', 'choihb@example.com',
     NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '김보겸', 'https://example.com/image.jpg', 'encoded_password_example', 'kimbk@example.com',
     NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '곽태영', 'https://example.com/image.jpg', 'encoded_password_example', 'kwakty@example.com',
     NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 'GUEST'),
    ('DEFAULT', 'sample_refresh_token', '서정식', 'https://example.com/image.jpg', 'encoded_password_example', 'seojs@example.com',
     NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 'GUEST');

-- facility
INSERT INTO facility (NAME, TYPE, CREATED_AT, MODIFIED_AT, FOR_SEARCH) VALUES
    ('무선 인터넷', 'INTERNET_ELECTRONICS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('TV', 'INTERNET_ELECTRONICS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('주방', 'ACCOMMODATION', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('세탁기', 'ACCOMMODATION', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('건조기', 'ACCOMMODATION', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('헤어드라이기', 'ACCOMMODATION', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('건물 내 무료 주차', 'ACCESSIBILITY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('건물 부지 내 유료 주차', 'ACCESSIBILITY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('에어컨', 'ACCOMMODATION', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('난방', 'ACCOMMODATION', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('수영장', 'ACTIVITY_LEISURE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('아기 침대', 'KID', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('헬스장', 'ACTIVITY_LEISURE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('바비큐 그릴', 'FOOD_DRINK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('아침식사', 'FOOD_DRINK', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('화재경보기', 'CLEAN_SAFETY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('구급 상자', 'CLEAN_SAFETY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('소화기', 'CLEAN_SAFETY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('일산화탄소 경보기', 'CLEAN_SAFETY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1)
    ;

-- fee_policy
INSERT INTO fee_policy (HOST_FEE_RATE, GUEST_FEE_RATE, START_DATE, END_DATE, CREATED_AT, MODIFIED_AT)
VALUES (0.03, 0.14, CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- discount_policy
INSERT INTO discount_policy (INITIAL_DISCOUNT_CNT, INITIAL_DISCOUNT_RATE, WEEKLY_DISCOUNT_RATE, MONTHLY_DISCOUNT_RATE,
                             START_DATE, END_DATE, CREATED_AT, MODIFIED_AT)
VALUES (0.03, 0.2, 0.1, 0.15, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
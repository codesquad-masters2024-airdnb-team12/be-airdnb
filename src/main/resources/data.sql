-- member
INSERT INTO member (LOGIN_TYPE, REFRESH_TOKEN, NAME, IMG_URL, ENCODED_PASSWORD, EMAIL,
                    ACCOUNT_NUMBER, MODIFIED_AT, DELETED_AT, CREATED_AT)
VALUES (
        'EMAIL', 'sample_refresh_token', 'John Doe', 'https://example.com/image.jpg', 'encoded_password_example', 'john.doe@example.com',
        NULL, '2024-06-10 12:34:56', NULL, '2024-06-10 12:00:00'
        );

-- hashtag
INSERT INTO hashtag (NAME, ICON, TYPE, CREATED_AT, MODIFIED_AT) VALUES
    -- type: amenity
    ('와이파이', '', 'AMENITY', '2024-06-10 12:34:56', '2024-06-10 12:34:56'),
    ('주방', '', 'AMENITY', '2024-06-10 12:34:56', '2024-06-10 12:34:56'),
    ('에어컨', '', 'AMENITY', '2024-06-10 12:34:56', '2024-06-10 12:34:56'),

    -- type: building_type
    ('아파트', '', 'BUILDING_TYPE', '2024-06-10 12:34:56', '2024-06-10 12:34:56'),

    -- type: accommodation_type
    ('건물 전체', '', 'ACCOMMODATION_TYPE', '2024-06-10 12:34:56', '2024-06-10 12:34:56');
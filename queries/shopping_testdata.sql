INSERT INTO USER_T (user_num, username, email, password, is_admin) VALUES (0, '관리자', 'admin.user@example.com', 'adminsecure123', 'Y');
INSERT INTO USER_T (user_num, username, email, password, is_admin) VALUES (1, '김철수', 'kim.cheolsu@example.com', 'password123', 'N');
INSERT INTO USER_T (user_num, username, email, password, is_admin) VALUES (2, '이영희', 'lee.younghee@example.com', 'securepass456', 'N');
INSERT INTO USER_T (user_num, username, email, password, is_admin) VALUES (3, '박민수', 'park.minsu@example.com', 'minsupwd789', 'N');
INSERT INTO USER_T (user_num, username, email, password, is_admin) VALUES (4, '최지은', 'choi.jieun@example.com', 'jiuen123', 'N');
INSERT INTO USER_T (user_num, username, email, password, is_admin) VALUES (5, '정수현', 'jung.suhyun@example.com', 'suhyun456', 'N');
INSERT INTO USER_T (user_num, username, email, password, is_admin) VALUES (6, '한예진', 'han.yejin@example.com', 'yejin789', 'N');
INSERT INTO USER_T (user_num, username, email, password, is_admin) VALUES (7, '조민준', 'jo.minjun@example.com', 'minjun123', 'N');
INSERT INTO USER_T (user_num, username, email, password, is_admin) VALUES (8, '장서연', 'jang.seoyeon@example.com', 'seoyeon456', 'N');
INSERT INTO USER_T (user_num, username, email, password, is_admin) VALUES (9, '오준호', 'oh.junho@example.com', 'junhopwd789', 'N');

INSERT INTO CATEGORY_T (category_id, name) VALUES (1, '전자제품');
INSERT INTO CATEGORY_T (category_id, name) VALUES (2, '패션');
INSERT INTO CATEGORY_T (category_id, name) VALUES (3, '가전제품');

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (1, '무선 마우스', '인체공학적 디자인의 고급 무선 마우스.', 25900, 100, 1, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (2, '블루투스 헤드폰', '노이즈 캔슬링 기능이 있는 오버이어 헤드폰.', 89900, 50, 1, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (3, '스마트폰 충전기', '고속 충전을 지원하는 USB-C 충전기.', 15900, 200, 1, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (4, '4K 모니터', '27인치 4K UHD 모니터로 선명한 색감을 제공합니다.', 299900, 30, 1, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (5, '게이밍 키보드', 'RGB 백라이트를 갖춘 기계식 키보드.', 59900, 150, 1, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (6, '남성용 티셔츠', '편안하고 통기성이 뛰어난 면 소재의 티셔츠.', 12900, 300, 2, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (7, '여성용 청바지', '스키니 핏 데님 청바지.', 49900, 100, 2, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (8, '가죽 재킷', '스타일리시한 블랙 가죽 재킷.', 119900, 20, 2, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (9, '스니커즈', '편안하고 내구성이 뛰어난 러닝화.', 69900, 150, 2, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (10, '손목시계', '클래식 아날로그 손목시계.', 99900, 75, 2, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (11, '진공청소기', '휴대용이며 강력한 진공청소기.', 129900, 50, 3, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (12, '전자레인지', '컴팩트하고 효율적인 전자레인지.', 89900, 40, 3, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (13, '공기청정기', '알레르기 및 오염 물질을 제거합니다.', 199900, 30, 3, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (14, '커피 메이커', '빠르고 효율적으로 커피를 내릴 수 있습니다.', 49900, 70, 3, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

INSERT INTO PRODUCT_T (PRODUCT_ID, NAME, DESCRIPTION, PRICE, STOCK, CATEGORY_ID, CREATED_AT)
VALUES (15, '냉장고', '에너지 효율적인 양문형 냉장고.', 599900, 10, 3, TO_DATE('2023-11-01', 'YYYY-MM-DD'));

-- [1] 쇼핑몰에 가입한 사용자들의 테이블

CREATE TABLE USER_T (
    user_num NUMBER PRIMARY KEY,
    username VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    password VARCHAR2(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_admin CHAR(1) DEFAULT 'N' CHECK (is_admin IN ('Y', 'N'))
);



--- [2] 상품 카테고리 테이블

CREATE TABLE CATEGORY_T (
    category_id NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    parent_id NUMBER,
    CONSTRAINT fk_parent_category FOREIGN KEY (parent_id) REFERENCES CATEGORY_T (category_id)
);



-- [3] 쇼핑몰에 판매 등록된 상품 테이블

CREATE TABLE PRODUCT_T (
    product_id NUMBER PRIMARY KEY,
    name VARCHAR2(200) NOT NULL,
    description CLOB,
    price NUMBER(12) NOT NULL,
    stock NUMBER DEFAULT 0,
    category_id NUMBER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES CATEGORY_T (category_id)
);



-- [4] 주문 테이블

CREATE TABLE ORDER_T (
    order_id NUMBER PRIMARY KEY,
    user_num NUMBER NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR2(50) DEFAULT 'Pending',
    total_amount NUMBER(17) NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_num) REFERENCES USER_T (user_num)
);



-- [5] 주문 하나 내에 포함된 상품 테이블 a.k.a. 주문 상세내역 테이블 

CREATE TABLE O_DETAIL_T (
    order_detail_id NUMBER PRIMARY KEY,
    order_id NUMBER NOT NULL,
    product_id NUMBER NOT NULL,
    quantity NUMBER DEFAULT 1,
    price NUMBER(12) NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES ORDER_T (order_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES PRODUCT_T (product_id)
);



-- [6] 사용자가 현재 담은 상품들을 나타내는 "장바구니"

CREATE TABLE CART_T (
    cart_id NUMBER PRIMARY KEY,
    user_num NUMBER NOT NULL,
    product_id NUMBER NOT NULL,
    quantity NUMBER DEFAULT 1,
    CONSTRAINT fk_cart_user FOREIGN KEY (user_num) REFERENCES USER_T (user_num),
    CONSTRAINT fk_cart_product FOREIGN KEY (product_id) REFERENCES PRODUCT_T (product_id)
);



-- [7] 상품을 구매한 사용자들이 남긴 상품평 / 리뷰

CREATE TABLE REVIEW_T (
    review_id NUMBER PRIMARY KEY,
    product_id NUMBER NOT NULL,
    user_num NUMBER NOT NULL,
    rating NUMBER(1) CHECK (rating BETWEEN 1 AND 5),
    comment_t CLOB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_review_product FOREIGN KEY (product_id) REFERENCES PRODUCT_T (product_id),
    CONSTRAINT fk_review_user FOREIGN KEY (user_num) REFERENCES USER_T (user_num)
);



-- [8] 결제 테이블

CREATE TABLE PAYMENT_T (
    payment_id NUMBER PRIMARY KEY,
    order_id NUMBER NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_method VARCHAR2(50),
    amount NUMBER(17) NOT NULL,
    CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES ORDER_T (order_id)
);



-- [9] 배송

CREATE TABLE SHIPPING_T (
    shipping_id NUMBER PRIMARY KEY,
    order_id NUMBER NOT NULL,
    tracking_number VARCHAR2(100),
    carrier VARCHAR2(100),
    status VARCHAR2(50) DEFAULT 'Pending',
    estimated_delivery DATE,
    CONSTRAINT fk_shipping_order FOREIGN KEY (order_id) REFERENCES ORDER_T (order_id)
);

# DB 설계

## ER-Diagram
![er diagram](https://github.com/liveforone/intelligent_commerce/assets/88976237/9d21b8b8-ab6d-43d1-86eb-a217bce07eed)

## 테이블 생성 및 제약조건 명시
### 회원 -> Member
```
create table member (
    id bigint not null auto_increment,
    auth varchar(255) not null,
    identity varchar(255) not null UNIQUE,
    email varchar(255) not null UNIQUE,
    password varchar(100) not null,
    bankbook_num varchar(255) not null,
    city varchar(255) not null,
    road_num(255) not null,
    detail(255) not null
    primary key (id)
);
CREATE INDEX email_idx ON member (email);
CREATE INDEX identity_idx ON member (identity);
```
### 마일리지 -> Mileage
```
create table mileage (
    id bigint not null auto_increment,
    member_identity varchar(255) not null UNIQUE,
    mileage_point bigint not null UNIQUE,
    FOREIGN KEY (member_identity) references MEMBER (identity),
    primary key (id)
);
CREATE INDEX member_identity_idx ON mileage (member_identity);
```
### 상점 -> Shop
```
create table shop (
    id bigint not null auto_increment,
    seller_identity varchar(255) not null UNIQUE,
    shop_name varchar(255) not null,
    tel varchar(255) not null,
    FOREIGN KEY (seller_identity) references MEMBER (identity),
    primary key (id)
);
CREATE INDEX seller_identity_idx ON shop (seller_identity);
```
### 상품 -> Item
```
create table item (
    id bigint not null auto_increment,
    shop_id bigint not null,
    title varchar(255) not null,
    content TEXT not null,
    price bigint not null,
    delivery_charge biging not null,
    remaining bigint not null,
    FOREIGN KEY (shop_id) references Shop (id),
    primary key (id)
);
CREATE INDEX shop_id_idx ON item (shop_id);
CREATE INDEX title_idx ON item (title);
```
### 주문 -> Orders
```
create table orders (
    id bigint not null auto_increment,
    member_identity varchar(255) not null UNIQUE,
    item_id bigint not null,
    total_price bigint not null,
    total_item_price bigint not null,
    spent_mileage bigint not null,
    order_quantity bigint not null,
    order_state varchar(255) not null, 
    created_date datetime(6) not null,
    FOREIGN KEY (member_identity) references MEMBER (identity),
    FOREIGN KEY (item_id) references ITEM (id),
    primary key (id)
);
CREATE INDEX member_identity_idx ON orders (member_identity);
CREATE INDEX item_id_idx ON orders (item_id);
```
### 리뷰 -> Review
```
create table review (
    id bigint not null auto_increment,
    order_id bigint not null UNIQUE,
    content TEXT not null,
    created_date datetime(6),
    FOREIGN KEY (order_id) references ORDERS (id),
    primary key (id)
);
CREATE INDEX order_id_idx ON orders (order_id);
```

## no-offset 페이징
* 페이징 성능을 향상하기 위해 no-offset 방식으로 페이징 처리한다.
* 이에 따라 동적쿼리 구성이 필요하다.
* 아래는 jdsl로 구성한 no-offset 동적쿼리이다.
* 현재 정렬은 desc이기 때문에 asc를 사용한다면 lessThan을 greaterThan으로 변경한다.
* 정책은 lastId가 0일경우 첫 페이지로 인식하고 null로 처리해 무시하도록 한다.
* 그 이외에는 lastId보다 작은 id에 한해 조회한다.
```
private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastId(lastId: Long): PredicateSpec? {
        return if (lastId == 0.toLong()) null
        else and(
            col(Item::id).lessThan(lastId)
        )
    }
```
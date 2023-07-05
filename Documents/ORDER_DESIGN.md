# 주문 설계

## 요구사항
* 주문한 회원 엔티티와 연관관계를 맺고있다.
* 주문한 상품 엔티티와 연관관계를 맺고있다.
* 페이징은 no-offset 방식으로 한다.
* 주문은 변경이 불가능합니다. 오로지 취소만 가능하며, 필드에 대한 변경이 금지됩니다. 이에 따라 모든 필드는 불변으로 선언됩니다.
* 오로지 주문 상태 필드만 가변입니다.
* 가격정보는 정확하고 보기쉽게 하기 위해서 사용한 마일리지, 상품 가격, 이를 모두 더한 총 금액, 이러한 3가지 값을 필드로 갖습니다.
* 가격에 대한것은 주문 엔티티에서 모두 직접 계산합니다.
* 주문 상태는 배송중, 배송완료, 취소가 있습니다.
* 주문은 삭제가 불가능하며, 취소시에는 주문 상태가 취소로 변경됩니다.
* 주문 수량과 사용할 마일리지는 null로 입력 가능하며, 기본값으로 각각 1, 0으로 초기화 됩니다.
* 주문 완료처리시에 주문이 취소이면 불가능합니다. 주문 완료는 상품을 등록한 판매자 회원이 요청 합니다.
* 주문 취소는 주문후 7일이내에 가능하며, 주문이 완료된 경우 불가능합니다. 주문 취소는 주문을 한 일반 회원이 요청 합니다.
* 주문시에 마일리지를 사용한다면 사용처리하고, 주문된 상품가격에 대한 마일리지를 적립하기 위해 상품 가격을 전송합니다.
* 또한 주문시에 상품의 재고를 주문 수량만큼 감소시키는 메소드도 호출합니다.
* 주문 취소시에는 재고와 마일리지를 모두 롤백합니다.

## API 설계
```
[GET] /order/detail/{id} : 주문 상세페이지, OrderInfo 형식 Json 리턴
[GET] /order/my-order : 나의 주문 페이지, OrderInfo 컬렉션 형식 Json 리턴
[GET] /order/shop/list : 상품에 속한 주문 페이지, OrderInfo 컬렉션 형식 Json 리턴
[POST] /order : CreateOrder 형식 Json 필요
[PUT] /order/delivery-completed/{id} : 주문 배송완료처리
[PUT] /order/cancel/{id} : 주문 취소처리
```

## Json Body 예시
```
{
  "itemId": 1,
  "orderQuantity": 1
}
```
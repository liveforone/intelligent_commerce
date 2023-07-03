# 상품 설계

## 요구사항
* 상점 엔티티와 다대일 관계를 맺고 있다.
* id와 shopId이외의 필드는 모두 var로 선언해 가변으로 설정한다.
* 현재 재고 - 구매수량이 0 미만이면 재고 마이너스가 불가능하다.
* 상품 삭제시 상품의 주인인지 체크한다.
* 재고 마이너스를 제외한 필드 업데이트시 상품의 주인인지 체크한다.
* 페이징은 no-offset 방식을 사용한다. 이에 따라 lastId를 처리하는 동적쿼리를 구성한다.

## API 설계
```
[GET] /item/detail/{id} : 상품 상세조회 페이지, ItemInfo 형식 Json 리턴
[GET] /item/home : 상품 홈, ItemInfo 컬렉션 형식 Json 리턴
[GET] /item/shop/{shopId} : 상점에 속한 상품 페이지, ItemInfo 컬렉션 형식 Json 리턴 
[GET] /item/search : 상품 검색 페이지, ItemInfo 컬렉션 형식 Json 리턴
[POST] /item/create : CreateItem 형식 Json 필요
[PUT] /item/update/title : UpdateItemTitle 형식 Json 필요
[PUT] /item/update/content : UpdateItemContent 형식 Json 필요
[PUT] /item/update/price : UpdatePrice 형식 Json 필요
[PUT] /item/update/delivery-charge : UpdateDeliveryCharge 형식 Json 필요
[PUT] /item/update/add-remaining : AddRemaining 형식 Json 필요
[DELETE] /item/delete/{id} : 상품 삭제
```

## Json Body 예시
```
[CreateItem]
{
  "title": "",
  "content": "",
  "price": 0,
  "deliveryCharge": 0,
  "remaining": 0
}

[AddRemaining]
{
  "id": 0,
  "remaining": 0
}

[UpdateDeliveryCharge]
{
  "id": 0,
  "deliveryCharge": 0
}

[UpdateItemContent]
{
  "id": 0,
  "content": ""
}

[UpdateItemTitle]
{
  "id": 0,
  "title": ""
}

[UpdatePrice]
{
  "id": 0,
  "price": 0
}
```
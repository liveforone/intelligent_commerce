# 상점 설계

## 요구사항
* 판매자 회원을 저장하기 위해 회원 엔티티와 1대1 연관관계를 맺는다.
* 판매자 권한을 갖는 회원만 접근 가능하다.
* 상점의 주인인지 체크하는 메서드를 가지고 있다.

## API 설계
```
[GET] /shop/{id} : ShopInfo 형식 Json 리턴, 상점 상세조회
[GET] /shop/info : ShopInfo 형식 Json 리턴, 주인의 상점 상세 조회
[POST] /shop/create : CreateShop 형식 Json 필요
[PUT] /shop/update/name : UpdateShopName 형식 Json 필요
[PUT] /shop/update/tel : UpdateTel 형식 Json 필요
```

## Json Body 예시
```
[CreateShop]
{
  "shopName": "test_shop",
  "tel": "0212345678"
}

[UpdateShopName]
{
  "shopName": "update_shop"
}

[UpdateTel]
{
  "tel": "01012345678"
}
```
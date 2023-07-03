# 리뷰 설계

## 요구사항
* 주문 엔티티와 1대1 연관관계를 맺습니다.
* 리뷰는 삭제하지 않는 한 박제와 같습니다. 이는 사용자로 하여금 함부로 리뷰를 달지 못하도록 경감심을 주기 위함입니다.
* 하나의 주문에 하나의 리뷰밖에 달지 못합니다.
* 리뷰는 id로 조회하는 리뷰상세, 주문으로 조회하는 리뷰상세, 상품으로 조회하는 리뷰 컬렉션이 있습니다.
* 리뷰를 등록할때에는 주문의 주인인지 체크합니다.

## API 설계
```
[GET] /review/detail/{id} : 리뷰 상세, ReviewInfo Json 형식 리턴
[GET] /review/order/{orderId} : 주문으로 리뷰 찾기, ReviewInfo Json 형식 리턴
[GET] /review/item/{itemId} : 상품으로 리뷰 찾기, ReviewInfo Json 형식 리턴
[POST] /review/create : CreateReview 형식 Json 필요
[DELETE] /review/delete/{id} : 리뷰 삭제
```

## Json Body 예시
```
[CreateReview]
{
  "orderId": 1,
  "content": "test_content_of_review"
}
```
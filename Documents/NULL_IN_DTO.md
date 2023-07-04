# dto에서 null 활용

## 전제 조건(배경)
* 필자는 DTO에서 spring-validation 어노테이션을 사용해 값을 검증 하고 있다.
* DTO는 당연히 개발자가 원하는 형태로 값이 들어오지 않을 것을 예상해야한다.
* 해당 문서는 그에 대한 이야기이다.

## 검증을 원하는 것
* null이 안되는 경우
* null이 가능한 경우
* 원하는 형태가 아닌 경우

## null이 안되는 경우
* null을 허용하고 싶지 않을때이다.
* 문자열의 경우 NotBlank를 활용하면 null, 공백, 스페이스로된 공백 등 다양한 상황을 검증해준다.
* 숫자형 타입의 경우 NotNull을 이용해서 검증이 가능하다.
* 이때 코틀린에서 null을 허용하는 타입인 ? 를 사용해서 받아야한다.
* 그렇지 않게 되면 값을 넣지 않고 요청을 했을때 검증되어 적절한 예외를 처리하게 되는것이 아닌,
* 에러를 맞이하게 된다.
```kotlin
data class CreateItem(
    @field:NotBlank(message = "상품명을 입력하세요.")
    var title: String?,
    @field:NotBlank(message = "상품 설명을 입력하세요.")
    var content: String?,
    @field:NotNull(message = "상품 가격을 입력하세요.")
    @field:Positive(message = "상품 가격은 양수만 가능합니다.")
    var price: Long?,
    @field:PositiveOrZero(message = "배송비는 양수 혹은 0만 가능합니다.")
    var deliveryCharge: Long?,
    @field:Positive(message = "재고는 양수만 가능합니다.")
    var remaining: Long?
)
```
* 위의 예제에서 title, content, price는 null이 불가능하여
* 값을 넣지 않는경우 바인딩 에러가 발생하여 처리하면되고,
* deliveryCharge, remaining의 경우 null을 허용하여 값을 넣지 않는경우 null로 입력된다.

## DTO 사용
* dto를 사용하는 곳에서는 이미 검증이 끝났기 때문에 null이 아니라는 확신의 의사전달이 가능하다.
* !! 키워드로 null이 아님을 표시한다.
```kotlin
fun createItem(createItem: CreateItem, identity: String): Long {
        return Item.create(
            shop = shopRepository.findOneByIdentity(identity),
            createItem.title!!,
            createItem.content!!,
            createItem.price!!,
            createItem.deliveryCharge,
            createItem.remaining
        )
}
```
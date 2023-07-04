# FK 원하는 값으로 지정

## FK 원하는 값 지정
* Jpa를 사용하다보면 연관관계를 맺을때 fk로 기본키(pk)가 지정된다.
* 그런데, 조건문에 fk를 사용하거나 하는 상황에서 fk로 기본키 대신에 원하는 값을 지정하고 싶을때가 있다.
* 아래는 그 방법과 주의 사항에 대한 정리이다.

## 사용방법
* referencedColumnName 을 사용하면 된다.
* 여기에 원하는 필드의 이름을 입력하면 해당 필드를 fk로 사용하게된다.
* 아래에서는 상수를 이용했고, 그냥 문자열을 사용해도 된다.
```kotlin
@JoinColumn(
    name = OrdersConstant.MEMBER_COLUMN_NAME,
    referencedColumnName = OrdersConstant.IDENTITY,
) val member: Member
```

## 주의점
* fk의 값을 원하는 값으로 둘때에 주의할점은 해당 값이 fk로써 적절한지 여부이다.
* 기본값이 pk인 이유는 해당값은 변경 불가능하기 때문이다. 이러한 fk로써 합당한 조건을 가져야한다.
* 필자의 경우 id가 아닌 uuid(identity 라는 필드명을 가진다.)를 사용하도록 변경했다.
* uuid는 중복되지 않고, 고유하며, 값의 변경이 일어나지 않기 때문에 fk로 쓰기 적절하다고 생각했다.
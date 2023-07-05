# Jdsl(Criteria)에서 Null을 허용하는 단건 쿼리 만들기

## 단건 쿼리가 null 이라면?
* Jdsl은 단건 조회시 jpa의 getSingleResult() 라는 함수를 사용한다.
* 이 함수의 심각한 문제는, 데이터가 없을 시에 NoResultException 이라는 예외를 터뜨린다는 것이다.
* 따라서 단건 조회 쿼리의 경우 아래와 같이 해당 예외를 적절히 처리하는 로직을 모두 둔다.
```kotlin
override fun findOneDtoById(id: Long): ShopInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Shop::id),
                    col(Shop::shopName),
                    col(Shop::tel)
                ))
                from(entity(Shop::class))
                where(col(Shop::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw ShopException(ShopExceptionMessage.SHOP_IS_NULL)
        }
    }
```
* 일반적인 상황에서 null에 대한 처리가 가능해지기 때문에 큰 문제가 없다.
* 자바 환경의 jpa와 query dsl 을 사용할때에는 optional을 바르느라 불편했기 때문이다.
* controller advice를 사용하게 되면 이러한 예외를 아주 우아하게 처리하기 때문에 기분도 좋다.

## 문제 발생
* 그러나 문제는 다음과 같은 상황에서 발생한다.
* 바로 null로 조회가 필요한 상황이다.
* 어떤 엔티티가 독자적으로 잘 움직이지 않고, 오로지 데이터를 분할하여 저장하는 형태로 있는 경우에
* 삭제나 생성은 다른 엔티티에서 처리한다고 가정해보자.
* 이때 삭제를 이벤트가 발생하면, jpa의 delete() 메서드를 사용하여 데이터베이스에서 조회하여 엔티티를 찾고
* delete() 메서드 안에 넣어주어 삭제하면된다.
* 그런데 만약 해당 데이터가 존재하지 않는다면 어떨까?
* 무시하면된다. 그런데 무시하지 못한다. jdsl이 사용하는 getSingleResult() 함수 정책때문에 NoResultException이 터진다.
* 이러한 경우가 바로 문제가 되는 경우고, 이때 null을 가져올 수 있는 쿼리가 필요하다.

## 리스트는 빈 값이 가능하다.
* 리스트는 빈 값으로 조회 가능하다. 당연한 이야기이다. 리스트는 비어있는게 가능하다.
* 단건 조회는 pk나, uuid 처럼 유일성이 보장되는 값으로 찾는다.
* 따라서 리스트 타입으로 단건조회를 하여 비어있는지 판단하고, 비어있다면 null을
* 비어있지 않다면 0번째 값을 리턴하도록 하면된다.
```
override fun findOneByIdentityNullable(identity: String): Shop? {
        val shop = queryFactory.listQuery {
            select(entity(Shop::class))
            from(entity(Shop::class))
            fetch(Shop::seller)
            join(Shop::seller, JoinType.INNER)
            where(nestedCol(col(Shop::seller), Member::identity).equal(identity))
        }

        return if (shop.isEmpty()) null
        else shop[ShopRepositoryConstant.FIRST_INDEX]
    }
```

## 사용
* 코틀린의 ?.스코프 함수 를 사용하면 null인 상황에서 효과적인 처리가 가능하다.
* null이면 무시하고, 아니면 함수를 호출하는 상황에서 아래와 같이 작성이 가능하다.
```
fun deleteShop(identity: String) {
        shopRepository.findOneByIdentityNullable(identity)
            ?.also {
                shopRepository.delete(it)
            }
    }
```
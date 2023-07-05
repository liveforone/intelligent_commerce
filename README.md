# Intelligent Commerce

## 목차
1. [프로젝트 소개](#1-프로젝트-소개)
2. [프로젝트 설계](#2-프로젝트-설계)
3. [고민점](#3-고민점)

## 1. 프로젝트 소개
### 소개
* 해당 프로젝트는 오픈마켓 플랫폼입니다.
* 상품을 주문하는 과정까지 담아보았습니다.
* 조회하는 쿼리가 많고, 연관관계가 복잡해서 쿼리의 성능을 높이고, 고민하는데에 시간을 많이 사용했습니다.
* 기존에 자바로만 만들었던 프로젝트를 간결하고 함수형을 지원하는 코틀린을 사용해 제작하였습니다.
### 기술 스택
* Framework : Spring Boot 3.1.1
* Lang : Kotlin, Jvm17
* Data : Spring Data Jpa & Kotlin-Jdsl & MySql
* Security : Spring Security & Jwt
* Test : Junit5

## 2. 프로젝트 설계
### 시스템 설계
* [아키텍처 설계](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/ARCITECTURE.md)
* [DB 설계](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/DB_DESIGN.md)
### 엔티티 설계
* [회원 설계](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/MEMBER_DESIGN.md)
* [마일리지 설계](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/MILEAGE_DESIGN.md)
* [상점 설계](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/SHOP_DESIGN.md)
* [상품 설계](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/ITEM_DESIGN.md)
* [주문 설계](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/ORDER_DESIGN.md)
* [리뷰 설계](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/REVIEW_DESIGN.md)
### 흐름도
* [전체 흐름도](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/FLOW.md)

## 3. 고민점
* [좋은 조인 쿼리 조건]()
* [원하는 값을 FK로 지정](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/WANT_VALUE_FOR_FK.md)
* [dto에서 null 활용](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/NULL_IN_DTO.md)
* [dsl(Criteria)에서 Null을 허용하는 단건 쿼리 만들기](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/NULLABLE_SINGLE_QUERY.md)

전체 코틀린 스러운 코드로 변환(itemcommanservice 이용하기)

takeIf 는 true 경우만 실행한다.
조건을 만족하지 않는경우에는 null을 반환한다.
또한 it 키워드의 사용이 가능하다.
뒤에 오는 함수의 경우 ?. 을 붙여서 사용해야한다. 반드시 .스코프 함수 만 사용하면 에러발생한다.

let: 주로 null 체크와 안전한 호출 연산을 함께 사용하여 null이 아닌 객체에 대한 작업을 수행할 때 유용합니다.
run: 객체의 함수를 호출하거나 값을 계산하는 블록을 실행하며, 결과를 반환합니다. 일반적으로 코드 블록 내에서 값을 초기화하거나 계산할 때 사용됩니다.
with: 수신 객체를 명시하고, 해당 객체의 함수를 호출하거나 속성에 직접 접근할 수 있습니다. 객체의 함수나 속성에 연속적으로 접근해야 할 때 유용합니다.
특히 파라미터롤 받은 dto.필드 처럼 사용할때 이 친구를 쓰면 아주 편하다.
apply: 수신 객체의 속성을 초기화하거나 설정할 때 사용됩니다. 주로 객체 생성 후 초기 설정을 수행할 때 유용합니다.
also: 수신 객체를 이용하여 추가적인 작업을 수행할 때 사용됩니다. 주로 객체의 속성을 변경하거나 부작용을 발생시키는 작업을 수행할 때 유용합니다.
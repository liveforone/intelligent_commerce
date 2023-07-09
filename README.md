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
* 최대한 코틀린의 다양한 기능들을 살려, 코틀린 스러운 코드를 만드는데에 힘썻습니다.
* 이러한 기술적 문제 외에도 장바구니와 관련하여 비즈니스 문제를 발견하고 이를 해결해 보았습니다.
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
### 기술적 고민
* [좋은 조인 쿼리 조건](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/GOOD_QUERY_AND_JOIN.md)
* [원하는 값을 FK로 지정](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/WANT_VALUE_FOR_FK.md)
* [dto에서 null 활용](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/NULL_IN_DTO.md)
* [dsl(Criteria)에서 Null을 허용하는 단건 쿼리 만들기](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/NULLABLE_SINGLE_QUERY.md)
### 비즈니스적 고민
* [장바구니가 수익창출에 효과가 있나?](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/USELESS_CART.md)

## 4. 리팩토링
* 해당 프로젝트는 지속적으로 리팩토링 됩니다. 
* 처음 설계한 것 이외에, 지속적으로 추가되는 리팩토링은 리팩토링 문서에 담아 기술하였습니다.
* [이메일은 유니크로 두지 않는다.]()
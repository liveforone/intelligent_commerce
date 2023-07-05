# 회원 설계

## 회원관리 기술
* 세션보다 가볍고 관리하기 편한 Jwt 토큰을 활용해 서버에 부담을 최대한 줄이는 방식을 채택하였습니다.
* 토큰관리는 가장 보편적인 방식인 프론트엔드의 로컬스토리지에서 jwt토큰을 관리하는것을 전제로 합니다.
* 토큰의 만료시간은 2시간 입니다.

## 상세 요구사항
* 회원은 어드민, 판매자, 일반 회원 총 3종류가 있다.
* 회원가입시 판매자와 일반회원 + 어드민은 서로 분리되어 진행한다.
* 일반 회원의 경우 회원가입하며 자동으로 마일리지를 생성한다. 판매자 회원은 생성하지 않는다.
* 로그인은 스프링 시큐리티에게 위임한다.
* 회원의 식별자는 identity 컬럼으로, uuid로 자동생성된 필드이다.
* 회원의 모든 이벤트는 identity를 기반으로 하며, 다른 엔티티에서 회원 엔티티와 연관관계를 맺을 때에도 
* 회원의 id가 아닌, identity 컬럼으로 연관관계를 맺도록 한다.
* 주소의 경우 embedded로 설정하여 깔끔하게 조회되도록 한다.
* 회원탈퇴시 일반회원의 경우 마일리지를, 판매자 회원의 경우 상점을 자동으로 삭제한다.

## 회원탈퇴시 마일리지/상점 삭제
* 마일리지는 일반회원으로 가입시 반드시 자동 생성된다.
* 그러나 판매자 회원(seller)으로 가입시 상점은 자동 생성되지 않는다.
* 상점은 판매자 회원이 직접 생성해야한다.
* 그런데, 회원 탈퇴시 마일리지/상점을 삭제 시킬때 상점 삭제를 주의해야한다.
* jpa의 delete() 메서드를 사용할때에는 엔티티를 찾아서 delete의 () 안에 넣어주면 삭제가 된다.
* 즉 조회를 한 번 해야한다.
* 그런데 jdsl은 단건 조회시 jpa criteria의  getSingleResult() 라는 함수를 사용해서 조회한다.
* 이 함수는 DB에서 조회된 값이 없으면 NoResultException 이라는 에러를 발생시킨다.
* 따라서 일반적으로 필자가 작성한 단건 조회 쿼리에는 이러한 예외시 적절하게 처리하는 로직을 두었다.
* 그러나 상점이 삭제될때는 상점을 조회해서 없으면 무시해버리면 그만인데, 이러한 예외가 터질 수 있다.
* 따라서 [Jdsl에서 null 허용하는 단건 쿼리 만들기](https://github.com/liveforone/intelligent_commerce/blob/master/Documents/NULLABLE_SINGLE_QUERY.md) 문서에서 볼 수 있듯 null을 허용하는 단건 쿼리를 만들어서 해결해야한다.

## API 설계
```
[POST] /member/signup : SignupRequest 형식 Json 필요
[POST] /member/signup/seller : SignupRequest 형식 Json 필요
[POST] /member/login : LoginRequest 형식 Json 필요
[GET] /member/info : MemberInfo 형식 Json 리턴
[PUT] /member/update/password : UpdatePassword 형식 Json 필요
[PUT] /member/update/bankbookNum : UpdateBankbookNum 형식 Json 필요
[PUT] /member/update/address : UpdateAddress 형식 Json 필요
[DELETE] /member/withdraw : String 형식 Json pw 필요
```

## Json body 예시
```
[SignupRequest]
{
  "email": "member1234@gmail.com",
  "pw": "1234",
  "bankbookNum": "1234567898765",
  "city": "서울",
  "roadNum": "종로-1-1",
  "detail": "101동 101호"
}

[LoginRequest]
{
  "email": "member1234@gmail.com",
  "pw": "1234"
}

[UpdatePassword]
{
  "password": "1111",
  "oldPassword": "1234"
}

[UpdateBankbookNum]
{
  "bankbookNum": "1239874650395"
}

[UpdateAddress]
{
  "city": "경기도",
  "roadNum": "대왕 판교로-1-1",
  "detail": "101동 701호"
}
```
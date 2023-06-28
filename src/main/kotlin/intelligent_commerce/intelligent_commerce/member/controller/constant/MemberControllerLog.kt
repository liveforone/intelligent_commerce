package intelligent_commerce.intelligent_commerce.member.controller.constant

enum class MemberControllerLog(val log: String) {
    SIGNUP_SUCCESS("회원가입 성공"), LOGIN_SUCCESS("로그인 성공"),
    UPDATE_PW_SUCCESS("비밀번호 변경 성공"), UPDATE_BANKBOOK_NUM_SUCCESS("계좌번호 변경 성공"),
    UPDATE_ADDRESS_SUCCESS("주소 변경 성공")
}
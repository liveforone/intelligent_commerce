package intelligent_commerce.intelligent_commerce.member.controller.response

import intelligent_commerce.intelligent_commerce.member.dto.response.MemberInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object MemberResponse {

    private const val SIGNUP_SUCCESS = "회원가입에 성공하였습니다.\n반갑습니다."
    private const val LOGIN_SUCCESS = "로그인에 성공하였습니다.\n환영합니다."
    private const val UPDATE_PW_SUCCESS = "비밀번호를 성공적으로 변경하였습니다."
    private const val UPDATE_BANKBOOK_NUM_SUCCESS = "계좌번호를 성공적으로 변경하였습니다."
    private const val UPDATE_ADDRESS_SUCCESS = "주소를 성공적으로 변경하였습니다."
    private const val WITHDRAW_SUCCESS = "회원탈퇴를 성공적으로 마쳤습니다.\n안녕히가세요."

    fun signupSuccess(): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(SIGNUP_SUCCESS)
    }

    fun loginSuccess(): ResponseEntity<*> = ResponseEntity.ok(LOGIN_SUCCESS)

    fun infoSuccess(member: MemberInfo): ResponseEntity<*> = ResponseEntity.ok(member)

    fun updatePwSuccess(): ResponseEntity<*> = ResponseEntity.ok(UPDATE_PW_SUCCESS)

    fun updateBankbookNumSuccess(): ResponseEntity<*> = ResponseEntity.ok(UPDATE_BANKBOOK_NUM_SUCCESS)

    fun updateAddressSuccess(): ResponseEntity<*> = ResponseEntity.ok(UPDATE_ADDRESS_SUCCESS)

    fun withdrawSuccess(): ResponseEntity<*> = ResponseEntity.ok(WITHDRAW_SUCCESS)
}
package intelligent_commerce.intelligent_commerce.member.dto.request

import jakarta.validation.constraints.NotBlank

data class WithdrawRequest(
    @field:NotBlank(message = "비밀번호를 입력하세요.")
    var pw: String?
)

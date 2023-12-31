package intelligent_commerce.intelligent_commerce.member.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignupRequest(
    @field:NotBlank(message = "이메일을 입력하세요.")
    var email: String?,
    @field:NotBlank(message = "비밀번호를 입력하세요.")
    var pw: String?,
    @field:NotBlank(message = "계좌번호를 입력하세요.")
    @field:Size(min = 13, max = 13)
    var bankbookNum: String?,
    @field:NotBlank(message = "도시를 입력하세요.")
    var city: String?,
    @field:NotBlank(message = "도로명 주소를 입력하세요.")
    var roadNum: String?,
    @field:NotBlank(message = "상세 주소를 입력하세요.")
    var detail: String?
)
package intelligent_commerce.intelligent_commerce.shop.dto.update

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateTel(
    @field:NotBlank(message = "변경할 전화번호를 입력하세요.")
    @field:Size(min = 8, message = "전화번호를 다시 확인해주세요.")
    var tel: String?
)

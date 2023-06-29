package intelligent_commerce.intelligent_commerce.shop.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateShop(
    @field:NotBlank(message = "상호명을 기입하세요.")
    var shopName: String?,
    @field:NotBlank(message = "전화번호를 기입하세요.")
    @field:Size(min = 8, message = "전화번호를 다시 확인해주세요.")
    var tel: String?
)

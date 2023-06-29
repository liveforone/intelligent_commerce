package intelligent_commerce.intelligent_commerce.shop.dto.update

import jakarta.validation.constraints.NotBlank

data class UpdateShopName(
    @field:NotBlank(message = "변경 할 상호명을 기입하세요.")
    var shopName: String?
)

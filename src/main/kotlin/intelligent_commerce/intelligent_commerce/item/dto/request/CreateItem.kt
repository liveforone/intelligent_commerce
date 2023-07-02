package intelligent_commerce.intelligent_commerce.item.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero

data class CreateItem(
    @field:NotBlank(message = "상품명을 입력하세요.")
    var title: String?,
    @field:NotBlank(message = "상품 설명을 입력하세요.")
    var content: String?,
    @field:Positive(message = "상품가격은 양수만 가능합니다.")
    var price: Long?,
    @field:PositiveOrZero(message = "배송비는 양수 혹은 0만 가능합니다.")
    var deliveryCharge: Long?,
    @field:Positive(message = "재고는 양수만 가능합니다.")
    var remaining: Long?
)

package intelligent_commerce.intelligent_commerce.item.dto.update

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class UpdatePrice(
    @field:NotNull(message = "상품 id를 입력하세요.")
    var id: Long?,
    @field:NotNull(message = "상품 가격을 입력하세요.")
    @field:Positive(message = "상품 가격은 양수만 가능합니다.")
    var price: Long?
)

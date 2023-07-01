package intelligent_commerce.intelligent_commerce.item.dto.update

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class AddRemaining(
    @field:NotNull(message = "상품 id를 입력하세요.")
    var id: Long?,
    @field:Positive(message = "재고는 양수만 가능합니다.")
    var remaining: Long?
)

package intelligent_commerce.intelligent_commerce.item.dto.update

import jakarta.validation.constraints.Positive

data class AddRemaining(
    @field:Positive(message = "재고는 양수만 가능합니다.")
    var remaining: ULong?
)

package intelligent_commerce.intelligent_commerce.order.dto.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class CreateOrder(
    @field:NotNull(message = "상품 id를 입력하세요.")
    var itemId: Long?,
    @field:Positive(message = "사용할 마일리지는 양수로 입력하세요.")
    var spentMileage: Long?,
    @field:Positive(message = "주문할 수량은 양수로 입력하세요.")
    var orderQuantity: Long?
)

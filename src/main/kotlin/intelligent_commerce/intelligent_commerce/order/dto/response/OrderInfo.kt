package intelligent_commerce.intelligent_commerce.order.dto.response

import intelligent_commerce.intelligent_commerce.order.domain.OrderState
import java.time.LocalDateTime

data class OrderInfo(
    val id: Long?,
    val itemId: Long,
    val totalPrice: Long,
    val spentMileage: Long,
    val orderQuantity: Long,
    val orderState: OrderState,
    val createdDate: LocalDateTime?
)

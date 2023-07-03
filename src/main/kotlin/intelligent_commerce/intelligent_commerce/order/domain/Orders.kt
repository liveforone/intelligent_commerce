package intelligent_commerce.intelligent_commerce.order.domain

import intelligent_commerce.intelligent_commerce.converter.OrderStateConverter
import intelligent_commerce.intelligent_commerce.exception.exception.OrdersException
import intelligent_commerce.intelligent_commerce.exception.message.OrdersExceptionMessage
import intelligent_commerce.intelligent_commerce.item.domain.Item
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.order.domain.constant.OrdersConstant
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
class Orders private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = OrdersConstant.MEMBER_COLUMN_NAME,
        referencedColumnName = OrdersConstant.IDENTITY,
        updatable = false
    ) val member: Member,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        updatable = false
    ) val item: Item,
    @Column(nullable = false) val totalPrice: Long,
    @Column(nullable = false) val totalItemPrice: Long,
    @Column(nullable = false) val spentMileage: Long,
    @Column(nullable = false) val orderQuantity: Long,
    @Convert(converter = OrderStateConverter::class) @Column(nullable = false) var orderState: OrderState,
    @CreatedDate @Column(nullable = false) val createdDate: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        private fun calculateTotalPrice(item: Item, spentMileage: Long?, orderQuantity: Long?): Long {
            val quantity = orderQuantity ?: OrdersConstant.BASIC_QUANTITY
            val mileage = spentMileage ?: OrdersConstant.BASIC_MILEAGE
            return (item.price * quantity) + item.deliveryCharge - mileage
        }

        private fun calculateTotalItemPrice(item: Item, orderQuantity: Long?): Long {
            val quantity = orderQuantity ?: OrdersConstant.BASIC_QUANTITY
            return item.price * quantity
        }

        fun create(member: Member, item: Item, spentMileage: Long?, orderQuantity: Long?): Orders {
            return Orders(
                id = null,
                member,
                item,
                totalPrice = calculateTotalPrice(item, spentMileage, orderQuantity),
                totalItemPrice = calculateTotalItemPrice(item, orderQuantity),
                spentMileage ?: OrdersConstant.BASIC_MILEAGE,
                orderQuantity ?: OrdersConstant.BASIC_QUANTITY,
                OrderState.DELIVERY_READY_AND_ING
            )
        }
    }

    fun deliveryCompleted() {
        if (orderState == OrderState.CANCEL) throw OrdersException(OrdersExceptionMessage.ALREADY_CANCELED)
        orderState = OrderState.DELIVERY_COMPLETED
    }

    fun cancelOrder() {
        if (orderState == OrderState.DELIVERY_COMPLETED) throw OrdersException(OrdersExceptionMessage.ALREADY_DELIVERY_COMPLETED)
        val limitDate = createdDate.toLocalDate().plusDays(OrdersConstant.LIMIT_CANCEL_DAY)
        val nowDate = LocalDate.now()
        if (nowDate.isAfter(limitDate)) throw OrdersException(OrdersExceptionMessage.OVER_CANCEL_LIMIT_DAY)
        orderState = OrderState.CANCEL
    }

    fun isOwnerOfOrder(identity: String): Boolean {
        return member.identity == identity
    }
}
package intelligent_commerce.intelligent_commerce.item.domain

import intelligent_commerce.intelligent_commerce.exception.exception.ItemException
import intelligent_commerce.intelligent_commerce.exception.message.ItemExceptionMessage
import intelligent_commerce.intelligent_commerce.item.domain.constant.ItemConstant
import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import jakarta.persistence.*

@Entity
class Item private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(
        updatable = false
    ) val shop: Shop,
    @Column(nullable = false) var title: String,
    @Column(nullable = false) var content: String,
    @Column(nullable = false) var price: Long,
    @Column(nullable = false) var deliveryCharge: Long,
    @Column(nullable = false) var remaining: Long
) {
    companion object {
        fun create(shop: Shop, title: String, content: String, price: Long, deliveryCharge: Long?, remaining: Long?): Item {
            return Item(
                id = null,
                shop,
                title,
                content,
                price,
                deliveryCharge ?: ItemConstant.DELIVERY_CHARGE_MINIMUM,
                remaining ?: ItemConstant.REMAINING_MINIMUM
            )
        }
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(content: String) {
        this.content = content
    }

    fun updatePrice(price: Long) {
        this.price = price
    }

    fun updateDeliveryCharge(deliveryCharge: Long) {
        this.deliveryCharge = deliveryCharge
    }

    fun addRemaining(remaining: Long) {
        this.remaining += remaining
    }

    fun minusRemaining(orderQuantity: Long) {
        if (remaining - orderQuantity < ItemConstant.REMAINING_MINIMUM) throw ItemException(ItemExceptionMessage.REMAINING_IS_ZERO)
        this.remaining -= orderQuantity
    }

    fun rollbackMinusRemaining(orderQuantity: Long) {
        this.remaining += orderQuantity
    }

    fun isOwnerOfItem(identity: String): Boolean {
        return shop.seller.identity == identity
    }
}
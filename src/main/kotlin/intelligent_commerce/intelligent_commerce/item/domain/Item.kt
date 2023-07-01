package intelligent_commerce.intelligent_commerce.item.domain

import intelligent_commerce.intelligent_commerce.exception.exception.ItemException
import intelligent_commerce.intelligent_commerce.exception.message.ItemExceptionMessage
import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import jakarta.persistence.*

@Entity
class Item private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn val shop: Shop,
    @Column(nullable = false) var title: String,
    @Column(nullable = false) var content: String,
    @Column(nullable = false) var price: Long,
    @Column(nullable = false) var remaining: Long
) {
    companion object {
        fun create(shop: Shop, title: String, content: String, price: Long, remaining: Long): Item {
            return Item(null, shop, title, content, price, remaining)
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

    fun addRemaining(remaining: Long) {
        this.remaining += remaining
    }

    fun minusRemaining() {
        if (remaining - 1 == 0.toLong()) throw ItemException(ItemExceptionMessage.REMAINING_IS_ZERO)
        this.remaining -= 1
    }

    fun rollbackMinusRemaining() {
        this.remaining += 1
    }

    fun isOwner(identity: String): Boolean {
        return this.shop.seller.identity == identity
    }
}
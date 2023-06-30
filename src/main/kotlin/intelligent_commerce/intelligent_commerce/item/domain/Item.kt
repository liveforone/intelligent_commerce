package intelligent_commerce.intelligent_commerce.item.domain

import intelligent_commerce.intelligent_commerce.exception.exception.ItemException
import intelligent_commerce.intelligent_commerce.exception.message.ItemExceptionMessage
import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import jakarta.persistence.*

@Entity
class Item private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) val shop: Shop,
    @Column(nullable = false) var title: String,
    @Column(nullable = false) var content: String,
    @Column(nullable = false) var price: ULong,
    @Column(nullable = false) var remaining: ULong
) {
    companion object {
        fun create(shop: Shop, title: String, content: String, price: ULong, remaining: ULong): Item {
            return Item(null, shop, title, content, price, remaining)
        }
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(content: String) {
        this.content = content
    }

    fun updatePrice(price: ULong) {
        this.price = price
    }

    fun addRemaining(remaining: ULong) {
        this.remaining += remaining
    }

    fun minusRemaining() {
        if (remaining - 1u == 0.toULong()) throw ItemException(ItemExceptionMessage.REMAINING_IS_ZERO)
        this.remaining -= 1u
    }

    fun rollbackMinusRemaining() {
        this.remaining += 1u
    }
}
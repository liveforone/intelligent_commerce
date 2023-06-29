package intelligent_commerce.intelligent_commerce.shop.domain

import intelligent_commerce.intelligent_commerce.exception.exception.ShopException
import intelligent_commerce.intelligent_commerce.exception.message.ShopExceptionMessage
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.domain.Role
import jakarta.persistence.*

@Entity
class Shop private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = "seller_identity",
        referencedColumnName = "identity"
    ) val seller: Member,
    var shopName: String,
    var tel: String
) {
    companion object {
        fun create(seller: Member, shopName: String, tel: String): Shop {
            if (seller.auth != Role.SELLER) throw ShopException(ShopExceptionMessage.AUTH_IS_NOT_SELLER)
            return Shop(null, seller, shopName, tel)
        }
    }

    fun updateShopName(shopName: String) {
        this.shopName = shopName
    }

    fun updateTel(tel: String) {
        this.tel = tel
    }
}
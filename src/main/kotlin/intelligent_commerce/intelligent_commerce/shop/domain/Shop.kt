package intelligent_commerce.intelligent_commerce.shop.domain

import intelligent_commerce.intelligent_commerce.exception.exception.ShopException
import intelligent_commerce.intelligent_commerce.exception.message.ShopExceptionMessage
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.domain.Role
import intelligent_commerce.intelligent_commerce.shop.domain.constant.ShopConstant
import jakarta.persistence.*

@Entity
class Shop private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = ShopConstant.SELLER_COLUMN_NAME,
        referencedColumnName = ShopConstant.IDENTITY,
        updatable = false,
        unique = true
    ) val seller: Member,
    @Column(nullable = false) var shopName: String,
    @Column(nullable = false) var tel: String
) {
    companion object {
        fun create(seller: Member, shopName: String, tel: String): Shop {
            require (seller.auth == Role.SELLER) { throw ShopException(ShopExceptionMessage.AUTH_IS_NOT_SELLER) }
            return Shop(id = null, seller, shopName, tel)
        }
    }

    fun updateShopName(shopName: String) {
        this.shopName = shopName
    }

    fun updateTel(tel: String) {
        this.tel = tel
    }
}
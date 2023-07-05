package intelligent_commerce.intelligent_commerce.shop.repository

import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import intelligent_commerce.intelligent_commerce.shop.dto.response.ShopInfo

interface ShopCustomRepository {

    fun findOneDtoById(id: Long): ShopInfo
    fun findOneByIdentity(identity: String): Shop
    fun findOneByIdentityNullable(identity: String): Shop?
    fun findOneDtoByIdentity(identity: String): ShopInfo
}
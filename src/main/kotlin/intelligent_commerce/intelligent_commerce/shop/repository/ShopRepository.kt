package intelligent_commerce.intelligent_commerce.shop.repository

import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import org.springframework.data.jpa.repository.JpaRepository

interface ShopRepository : JpaRepository<Shop, Long>, ShopCustomRepository {
}
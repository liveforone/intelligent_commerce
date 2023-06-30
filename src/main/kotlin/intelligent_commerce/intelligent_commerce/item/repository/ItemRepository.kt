package intelligent_commerce.intelligent_commerce.item.repository

import intelligent_commerce.intelligent_commerce.item.domain.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, Long>, ItemCustomRepository {
}
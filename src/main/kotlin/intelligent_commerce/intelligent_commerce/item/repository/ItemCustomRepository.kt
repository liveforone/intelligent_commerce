package intelligent_commerce.intelligent_commerce.item.repository

import intelligent_commerce.intelligent_commerce.item.domain.Item
import intelligent_commerce.intelligent_commerce.item.dto.response.ItemInfo

interface ItemCustomRepository {
    fun findOneById(id: Long): Item
    fun findOneByIdJoinShopAndMember(id: Long): Item
    fun findOneDtoById(id: Long): ItemInfo
    fun findAllItems(lastId: Long): List<ItemInfo>
    fun findItemsByShopId(shopId: Long, lastId: Long): List<ItemInfo>
    fun searchItemsByKeyword(keyword: String, lastId: Long): List<ItemInfo>
    fun deleteAllByShopIdBatch(shopId: Long)
}
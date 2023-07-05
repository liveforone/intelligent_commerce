package intelligent_commerce.intelligent_commerce.item.service.query

import intelligent_commerce.intelligent_commerce.item.dto.response.ItemInfo
import intelligent_commerce.intelligent_commerce.item.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemQueryService @Autowired constructor(
    private val itemRepository: ItemRepository
) {

    fun getItemById(id: Long): ItemInfo = itemRepository.findOneDtoById(id)

    fun getAllItems(lastId: Long): List<ItemInfo> = itemRepository.findAllItems(lastId)

    fun getItemsByShop(shopId: Long, lastId: Long): List<ItemInfo> {
        return itemRepository.findItemsByShopId(shopId, lastId)
    }

    fun searchItems(keyword: String, lastId: Long): List<ItemInfo> {
        return itemRepository.searchItemsByKeyword(keyword, lastId)
    }
}
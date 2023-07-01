package intelligent_commerce.intelligent_commerce.item.service.command

import intelligent_commerce.intelligent_commerce.exception.exception.ItemException
import intelligent_commerce.intelligent_commerce.exception.message.ItemExceptionMessage
import intelligent_commerce.intelligent_commerce.item.domain.Item
import intelligent_commerce.intelligent_commerce.item.dto.request.CreateItem
import intelligent_commerce.intelligent_commerce.item.dto.update.AddRemaining
import intelligent_commerce.intelligent_commerce.item.dto.update.UpdateItemContent
import intelligent_commerce.intelligent_commerce.item.dto.update.UpdateItemTitle
import intelligent_commerce.intelligent_commerce.item.dto.update.UpdatePrice
import intelligent_commerce.intelligent_commerce.item.repository.ItemRepository
import intelligent_commerce.intelligent_commerce.shop.repository.ShopRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ItemCommandService @Autowired constructor(
    private val itemRepository: ItemRepository,
    private val shopRepository: ShopRepository
) {

    fun createItem(createItem: CreateItem, identity: String): Long {
        return Item.create(
            shopRepository.findOneByIdentity(identity),
            createItem.title!!,
            createItem.content!!,
            createItem.price!!,
            createItem.remaining!!
        ).also {
            itemRepository.save(it)
        }.id!!
    }

    fun updateTitle(requestDto: UpdateItemTitle, identity: String) {
        itemRepository.findOneByIdJoinShopAndMember(requestDto.id!!).also {
            if (!it.isOwner(identity)) throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
            it.updateTitle(requestDto.title!!)
        }
    }

    fun updateContent(requestDto: UpdateItemContent, identity: String) {
        itemRepository.findOneByIdJoinShopAndMember(requestDto.id!!).also {
            if (!it.isOwner(identity)) throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
            it.updateContent(requestDto.content!!)
        }
    }

    fun updatePrice(requestDto: UpdatePrice, identity: String) {
        itemRepository.findOneByIdJoinShopAndMember(requestDto.id!!).also {
            if (!it.isOwner(identity)) throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
            it.updatePrice(requestDto.price!!)
        }
    }

    fun addRemaining(requestDto: AddRemaining, identity: String) {
        itemRepository.findOneByIdJoinShopAndMember(requestDto.id!!).also {
            if (!it.isOwner(identity)) throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
            it.addRemaining(requestDto.remaining!!)
        }
    }

    fun minusRemaining(id: Long) {
        itemRepository.findOneById(id).also {
            it.minusRemaining()
        }
    }

    fun rollbackMinusRemaining(id: Long) {
        itemRepository.findOneById(id).also {
            it.rollbackMinusRemaining()
        }
    }

    fun deleteItem(identity: String, id: Long) {
        itemRepository.findOneByIdJoinShopAndMember(id).also {
            if (!it.isOwner(identity)) throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
            itemRepository.delete(it)
        }
    }

    fun deleteItemsByShopId(shopId: Long) {
        itemRepository.deleteAllByShopIdBatch(shopId)
    }
}
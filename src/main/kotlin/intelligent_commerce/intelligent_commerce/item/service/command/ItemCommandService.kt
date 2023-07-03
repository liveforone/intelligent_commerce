package intelligent_commerce.intelligent_commerce.item.service.command

import intelligent_commerce.intelligent_commerce.exception.exception.ItemException
import intelligent_commerce.intelligent_commerce.exception.message.ItemExceptionMessage
import intelligent_commerce.intelligent_commerce.item.domain.Item
import intelligent_commerce.intelligent_commerce.item.dto.request.CreateItem
import intelligent_commerce.intelligent_commerce.item.dto.update.*
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
            createItem.deliveryCharge!!,
            createItem.remaining!!
        ).also {
            itemRepository.save(it)
        }.id!!
    }

    fun updateTitle(requestDto: UpdateItemTitle, identity: String) {
        itemRepository.findOneByIdJoinSeller(requestDto.id!!).also {
            if (!it.shop.isOwnerOfShop(identity)) throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
            it.updateTitle(requestDto.title!!)
        }
    }

    fun updateContent(requestDto: UpdateItemContent, identity: String) {
        itemRepository.findOneByIdJoinSeller(requestDto.id!!).also {
            if (!it.shop.isOwnerOfShop(identity)) throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
            it.updateContent(requestDto.content!!)
        }
    }

    fun updatePrice(requestDto: UpdatePrice, identity: String) {
        itemRepository.findOneByIdJoinSeller(requestDto.id!!).also {
            if (!it.shop.isOwnerOfShop(identity)) throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
            it.updatePrice(requestDto.price!!)
        }
    }

    fun updateDeliverCharge(requestDto: UpdateDeliveryCharge, identity: String) {
        itemRepository.findOneByIdJoinSeller(requestDto.id!!).also {
            if (!it.shop.isOwnerOfShop(identity)) throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
            it.updateDeliveryCharge(requestDto.deliveryCharge!!)
        }
    }

    fun addRemaining(requestDto: AddRemaining, identity: String) {
        itemRepository.findOneByIdJoinSeller(requestDto.id!!).also {
            if (!it.shop.isOwnerOfShop(identity)) throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
            it.addRemaining(requestDto.remaining!!)
        }
    }

    fun minusRemaining(orderQuantity: Long, id: Long) {
        itemRepository.findOneById(id).also {
            it.minusRemaining(orderQuantity)
        }
    }

    fun rollbackMinusRemaining(orderQuantity: Long, id: Long) {
        itemRepository.findOneById(id).also {
            it.rollbackMinusRemaining(orderQuantity)
        }
    }

    fun deleteItem(identity: String, id: Long) {
        itemRepository.findOneByIdJoinSeller(id).also {
            if (!it.shop.isOwnerOfShop(identity)) throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
            itemRepository.delete(it)
        }
    }
}
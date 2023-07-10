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
        return with(createItem) {
            Item.create(
                shop = shopRepository.findOneByIdentity(identity),
                title!!,
                content!!,
                price!!,
                deliveryCharge,
                remaining
            ).run { itemRepository.save(this).id!! }
        }
    }

    fun updateTitle(requestDto: UpdateItemTitle, identity: String) {
        with(requestDto) {
            itemRepository.findOneByIdJoinSeller(id!!)
                .takeIf { it.isOwnerOfItem(identity) }
                ?.also { it.updateTitle(title!!) }
                ?: throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
        }
    }

    fun updateContent(requestDto: UpdateItemContent, identity: String) {
        with(requestDto) {
            itemRepository.findOneByIdJoinSeller(id!!)
                .takeIf { it.isOwnerOfItem(identity) }
                ?.also { it.updateContent(content!!) }
                ?: throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
        }
    }

    fun updatePrice(requestDto: UpdatePrice, identity: String) {
        with(requestDto) {
            itemRepository.findOneByIdJoinSeller(id!!)
                .takeIf { it.isOwnerOfItem(identity) }
                ?.also { it.updatePrice(price!!) }
                ?: throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
        }
    }

    fun updateDeliverCharge(requestDto: UpdateDeliveryCharge, identity: String) {
        with(requestDto) {
            itemRepository.findOneByIdJoinSeller(id!!)
                .takeIf { it.isOwnerOfItem(identity) }
                ?.also { it.updateDeliveryCharge(deliveryCharge!!) }
                ?: throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
        }
    }

    fun addRemaining(requestDto: AddRemaining, identity: String) {
        with(requestDto) {
            itemRepository.findOneByIdJoinSeller(id!!)
                .takeIf { it.isOwnerOfItem(identity) }
                ?.also { it.addRemaining(remaining!!) }
                ?: throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
        }
    }

    fun minusRemaining(orderQuantity: Long, id: Long) {
        itemRepository.findOneById(id)
            .also { it.minusRemaining(orderQuantity) }
    }

    fun rollbackMinusRemaining(orderQuantity: Long, id: Long) {
        itemRepository.findOneById(id)
            .also { it.rollbackMinusRemaining(orderQuantity) }
    }

    fun deleteItem(identity: String, id: Long) {
        itemRepository.findOneByIdJoinSeller(id)
            .takeIf { it.isOwnerOfItem(identity) }
            ?. also { itemRepository.delete(it) }
            ?: throw ItemException(ItemExceptionMessage.NOT_OWNER_OF_ITEM)
    }
}
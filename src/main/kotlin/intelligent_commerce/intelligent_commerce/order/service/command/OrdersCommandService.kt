package intelligent_commerce.intelligent_commerce.order.service.command

import intelligent_commerce.intelligent_commerce.exception.exception.OrdersException
import intelligent_commerce.intelligent_commerce.exception.message.OrdersExceptionMessage
import intelligent_commerce.intelligent_commerce.item.repository.ItemRepository
import intelligent_commerce.intelligent_commerce.item.service.command.ItemCommandService
import intelligent_commerce.intelligent_commerce.member.repository.MemberRepository
import intelligent_commerce.intelligent_commerce.mileage.service.command.MileageCommandService
import intelligent_commerce.intelligent_commerce.order.domain.Orders
import intelligent_commerce.intelligent_commerce.order.dto.request.CreateOrder
import intelligent_commerce.intelligent_commerce.order.repository.OrdersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrdersCommandService @Autowired constructor(
    private val ordersRepository: OrdersRepository,
    private val memberRepository: MemberRepository,
    private val itemRepository: ItemRepository,
    private val mileageCommandService: MileageCommandService,
    private val itemCommandService: ItemCommandService
) {

    fun createOrder(requestDto: CreateOrder, identity: String): Long {
        return Orders.create(
            memberRepository.findOneByIdentity(identity),
            itemRepository.findOneById(requestDto.itemId!!),
            requestDto.spentMileage,
            requestDto.orderQuantity
        ).also {
            itemCommandService.minusRemaining(it.orderQuantity, it.item.id!!)
            requestDto.spentMileage?.let {
                mileageCommandService.subtractPoint(requestDto.spentMileage!!, identity)
            }
            ordersRepository.save(it)
            mileageCommandService.addPoint(it.totalItemPrice, identity)
        }.id!!
    }

    fun deliveryCompleted(id: Long, sellerIdentity: String) {
        ordersRepository.findOneByIdJoinSeller(id).also {
            if (!it.item.shop.isOwnerOfShop(sellerIdentity)) throw OrdersException(OrdersExceptionMessage.NOT_OWNER_OF_ITEM)
            it.deliveryCompleted()
        }
    }

    fun cancelOrderByMember(id: Long, identity: String) {
        ordersRepository.findOneByIdJoinMember(id).also {
            if (!it.isOwnerOfOrder(identity)) throw OrdersException(OrdersExceptionMessage.NOT_OWNER_OF_ORDER)
            itemCommandService.rollbackMinusRemaining(it.orderQuantity, it.item.id!!)
            mileageCommandService.rollbackMileage(it.spentMileage, it.totalPrice, identity)
            it.cancelOrder()
        }
    }
}
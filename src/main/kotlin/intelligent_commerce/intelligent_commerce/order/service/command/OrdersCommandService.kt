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
        return with(requestDto) {
            Orders.create(
                member = memberRepository.findOneByIdentity(identity),
                item = itemRepository.findOneById(itemId!!),
                spentMileage,
                orderQuantity
            ).run {
                itemCommandService.minusRemaining(orderQuantity, item.id!!)
                requestDto.spentMileage?.let {
                        mileage -> mileageCommandService.subtractPoint(mileage, identity)
                }
                mileageCommandService.addPoint(totalItemPrice, identity)
                ordersRepository.save(this).id!!
            }
        }
    }

    fun deliveryCompleted(id: Long, sellerIdentity: String) {
        ordersRepository.findOneByIdJoinSeller(id)
            .takeIf { it.item.isOwnerOfItem(sellerIdentity) }
            ?.also { it.deliveryCompleted() }
            ?: throw OrdersException(OrdersExceptionMessage.NOT_OWNER_OF_ITEM)
    }

    fun cancelOrderByMember(id: Long, identity: String) {
        ordersRepository.findOneById(id)
            .takeIf { it.isOwnerOfOrder(identity) }
            ?.also {
                itemCommandService.rollbackMinusRemaining(it.orderQuantity, it.item.id!!)
                mileageCommandService.rollbackMileage(it.spentMileage, it.totalPrice, identity)
                it.cancelOrder()
            }
            ?: throw OrdersException(OrdersExceptionMessage.NOT_OWNER_OF_ORDER)
    }
}
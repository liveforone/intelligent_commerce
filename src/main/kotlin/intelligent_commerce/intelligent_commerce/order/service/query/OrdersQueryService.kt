package intelligent_commerce.intelligent_commerce.order.service.query

import intelligent_commerce.intelligent_commerce.order.dto.response.OrderInfo
import intelligent_commerce.intelligent_commerce.order.repository.OrdersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrdersQueryService @Autowired constructor(
    private val ordersRepository: OrdersRepository
) {

    fun getOrderById(id: Long) = ordersRepository.findOneDtoById(id)

    fun getOrdersByIdentity(lastId: Long, identity: String): List<OrderInfo> {
        return ordersRepository.findOrdersByIdentity(lastId, identity)
    }

    fun getOrdersBySeller(lastId: Long, sellerIdentity: String): List<OrderInfo> {
        return ordersRepository.findOrdersBySellerIdentityJoinSeller(lastId, sellerIdentity)
    }
}
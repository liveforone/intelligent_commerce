package intelligent_commerce.intelligent_commerce.order.repository

import intelligent_commerce.intelligent_commerce.order.domain.Orders
import intelligent_commerce.intelligent_commerce.order.dto.response.OrderInfo

interface OrdersCustomRepository {
    fun findOneById(id: Long): Orders
    fun findOneDtoById(id: Long): OrderInfo
    fun findOneByIdJoinSeller(id: Long): Orders
    fun findOrdersByIdentity(lastId: Long?, identity: String): List<OrderInfo>
    fun findOrdersBySellerIdentityJoinSeller(lastId: Long?, sellerIdentity: String): List<OrderInfo>
}
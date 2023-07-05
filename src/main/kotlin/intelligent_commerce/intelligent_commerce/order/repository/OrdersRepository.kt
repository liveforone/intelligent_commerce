package intelligent_commerce.intelligent_commerce.order.repository

import intelligent_commerce.intelligent_commerce.order.domain.Orders
import org.springframework.data.jpa.repository.JpaRepository

interface OrdersRepository : JpaRepository<Orders, Long>, OrdersCustomRepository
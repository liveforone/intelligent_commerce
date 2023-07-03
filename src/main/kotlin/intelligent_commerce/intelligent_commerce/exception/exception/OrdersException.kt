package intelligent_commerce.intelligent_commerce.exception.exception

import intelligent_commerce.intelligent_commerce.exception.message.OrdersExceptionMessage
import java.lang.RuntimeException

class OrdersException(val ordersExceptionMessage: OrdersExceptionMessage) : RuntimeException(ordersExceptionMessage.message) {
}
package intelligent_commerce.intelligent_commerce.exception.controllerAdvice

import intelligent_commerce.intelligent_commerce.exception.exception.OrdersException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class OrdersControllerAdvice {

    @ExceptionHandler(OrdersException::class)
    fun ordersExceptionHandle(ordersException: OrdersException): ResponseEntity<*> {
        return ResponseEntity
            .status(ordersException.ordersExceptionMessage.status)
            .body(ordersException.message)
    }
}
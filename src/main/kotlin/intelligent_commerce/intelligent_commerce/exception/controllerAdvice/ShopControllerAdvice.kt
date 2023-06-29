package intelligent_commerce.intelligent_commerce.exception.controllerAdvice

import intelligent_commerce.intelligent_commerce.exception.exception.ShopException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ShopControllerAdvice {

    @ExceptionHandler(ShopException::class)
    fun shopExceptionHandle(shopException: ShopException): ResponseEntity<*> {
        return ResponseEntity
            .status(shopException.shopExceptionMessage.status)
            .body(shopException.message)
    }
}
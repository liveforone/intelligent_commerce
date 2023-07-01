package intelligent_commerce.intelligent_commerce.exception.controllerAdvice

import intelligent_commerce.intelligent_commerce.exception.exception.ItemException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ItemControllerAdvice {

    @ExceptionHandler(ItemException::class)
    fun itemExceptionHandle(itemException: ItemException): ResponseEntity<*> {
        return ResponseEntity
            .status(itemException.itemExceptionMessage.status)
            .body(itemException.message)
    }
}
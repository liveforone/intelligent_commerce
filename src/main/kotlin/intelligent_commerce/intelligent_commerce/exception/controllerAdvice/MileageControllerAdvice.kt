package intelligent_commerce.intelligent_commerce.exception.controllerAdvice

import intelligent_commerce.intelligent_commerce.exception.exception.MileageException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class MileageControllerAdvice {

    @ExceptionHandler(MileageException::class)
    fun mileageExceptionHandle(mileageException: MileageException): ResponseEntity<*> {
        return ResponseEntity
            .status(mileageException.mileageExceptionMessage.status)
            .body(mileageException.message)
    }
}
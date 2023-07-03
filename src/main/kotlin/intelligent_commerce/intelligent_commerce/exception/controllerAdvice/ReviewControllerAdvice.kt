package intelligent_commerce.intelligent_commerce.exception.controllerAdvice

import intelligent_commerce.intelligent_commerce.exception.exception.ReviewException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ReviewControllerAdvice {

    @ExceptionHandler(ReviewException::class)
    fun reviewExceptionHandle(reviewException: ReviewException): ResponseEntity<*> {
        return ResponseEntity
            .status(reviewException.reviewExceptionMessage.status)
            .body(reviewException.message)
    }
}
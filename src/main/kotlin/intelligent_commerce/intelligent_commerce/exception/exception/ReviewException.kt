package intelligent_commerce.intelligent_commerce.exception.exception

import intelligent_commerce.intelligent_commerce.exception.message.ReviewExceptionMessage
import java.lang.RuntimeException

class ReviewException(val reviewExceptionMessage: ReviewExceptionMessage) : RuntimeException(reviewExceptionMessage.message) {
}
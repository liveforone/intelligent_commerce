package intelligent_commerce.intelligent_commerce.review.dto.response

import java.time.LocalDateTime

data class ReviewInfo(
    val orderId: Long?,
    val content: String,
    val createdDate: LocalDateTime
)

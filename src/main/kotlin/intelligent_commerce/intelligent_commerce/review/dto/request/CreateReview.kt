package intelligent_commerce.intelligent_commerce.review.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateReview(
    @field:NotNull(message = "주문 id를 입력하세요.")
    var orderId: Long?,
    @field:NotBlank(message = "리뷰를 작성하세요.")
    var content: String?
)
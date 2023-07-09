package intelligent_commerce.intelligent_commerce.review.controller.response

import intelligent_commerce.intelligent_commerce.review.dto.response.ReviewInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ReviewResponse {

    private const val CREATE_REVIEW_SUCCESS = "리뷰를 성공적으로 등록했습니다."
    private const val DELETE_REVIEW_SUCCESS = "리뷰를 성공적으로 삭제했습니다."

    fun reviewDetailSuccess(reviewInfo: ReviewInfo) = ResponseEntity.ok(reviewInfo)

    fun reviewByOrderSuccess(reviewInfo: ReviewInfo) = ResponseEntity.ok(reviewInfo)

    fun reviewsByItemSuccess(reviews: List<ReviewInfo>) = ResponseEntity.ok(reviews)
    fun createReviewSuccess(): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(CREATE_REVIEW_SUCCESS)
    }

    fun deleteReviewSuccess() = ResponseEntity.ok(DELETE_REVIEW_SUCCESS)
}
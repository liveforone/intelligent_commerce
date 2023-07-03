package intelligent_commerce.intelligent_commerce.exception.message

enum class ReviewExceptionMessage(val status: Int, val message: String) {
    REVIEW_IS_NULL(404, "리뷰가 존재하지 않습니다."),
    NOT_OWNER_OF_REVIEW(403, "리뷰의 작성자가 아닙니다.")
}
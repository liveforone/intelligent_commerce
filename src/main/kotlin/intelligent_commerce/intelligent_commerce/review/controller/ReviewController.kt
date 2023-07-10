package intelligent_commerce.intelligent_commerce.review.controller

import intelligent_commerce.intelligent_commerce.logger
import intelligent_commerce.intelligent_commerce.review.controller.constant.ReviewControllerLog
import intelligent_commerce.intelligent_commerce.review.controller.constant.ReviewParam
import intelligent_commerce.intelligent_commerce.review.controller.constant.ReviewUrl
import intelligent_commerce.intelligent_commerce.review.controller.response.ReviewResponse
import intelligent_commerce.intelligent_commerce.review.dto.request.CreateReview
import intelligent_commerce.intelligent_commerce.review.service.command.ReviewCommandService
import intelligent_commerce.intelligent_commerce.review.service.query.ReviewQueryService
import intelligent_commerce.intelligent_commerce.validator.ControllerValidator
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class ReviewController @Autowired constructor(
    private val reviewQueryService: ReviewQueryService,
    private val reviewCommandService: ReviewCommandService,
    private val controllerValidator: ControllerValidator
) {

    @GetMapping(ReviewUrl.REVIEW_DETAIL)
    fun reviewDetail(@PathVariable(ReviewParam.ID) id: Long): ResponseEntity<*> {
        val review = reviewQueryService.getReviewById(id)
        return ReviewResponse.reviewDetailSuccess(review)
    }

    @GetMapping(ReviewUrl.REVIEW_BY_ORDER)
    fun reviewByOrder(@PathVariable(ReviewParam.ORDER_ID) orderId: Long): ResponseEntity<*> {
        val review = reviewQueryService.getReviewByOrder(orderId)
        return ReviewResponse.reviewByOrderSuccess(review)
    }

    @GetMapping(ReviewUrl.REVIEWS_BY_ITEM)
    fun reviewsByItem(
        @PathVariable(ReviewParam.ITEM_ID) itemId: Long,
        @RequestParam(ReviewParam.LAST_ID, required = false) lastId: Long?
    ): ResponseEntity<*> {
        val reviews = reviewQueryService.getReviewsByItem(lastId, itemId)
        return ReviewResponse.reviewsByItemSuccess(reviews)
    }

    @PostMapping(ReviewUrl.CREATE_REVIEW)
    fun createReview(
        @RequestBody @Valid createReview: CreateReview,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        reviewCommandService.createReview(createReview)
        logger().info(ReviewControllerLog.CREATE_REVIEW_SUCCESS.log)

        return ReviewResponse.createReviewSuccess()
    }

    @DeleteMapping(ReviewUrl.DELETE_REVIEW)
    fun deleteReview(
        @PathVariable(ReviewParam.ID) id: Long,
        principal: Principal
    ): ResponseEntity<*> {
        reviewCommandService.deleteReview(id, identity = principal.name)
        logger().info(ReviewControllerLog.DELETE_REVIEW_SUCCESS.log)

        return ReviewResponse.deleteReviewSuccess()
    }
}
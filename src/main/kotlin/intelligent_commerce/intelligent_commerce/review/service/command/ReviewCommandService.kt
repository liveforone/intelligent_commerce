package intelligent_commerce.intelligent_commerce.review.service.command

import intelligent_commerce.intelligent_commerce.exception.exception.ReviewException
import intelligent_commerce.intelligent_commerce.exception.message.ReviewExceptionMessage
import intelligent_commerce.intelligent_commerce.order.repository.OrdersRepository
import intelligent_commerce.intelligent_commerce.review.domain.Review
import intelligent_commerce.intelligent_commerce.review.dto.request.CreateReview
import intelligent_commerce.intelligent_commerce.review.repository.ReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReviewCommandService @Autowired constructor(
    private val reviewRepository: ReviewRepository,
    private val ordersRepository: OrdersRepository
) {

    fun createReview(requestDto: CreateReview) {
        Review.create(
            order = ordersRepository.findOneById(requestDto.orderId!!),
            requestDto.content!!
        ).also {
            reviewRepository.save(it)
        }
    }

    fun deleteReview(id: Long, identity: String) {
        reviewRepository.findOneByIdJoinMember(id)
            .also {
                if (!it.isOwnerOfReview(identity)) throw ReviewException(ReviewExceptionMessage.NOT_OWNER_OF_REVIEW)
                reviewRepository.delete(it)
            }
    }
}
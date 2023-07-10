package intelligent_commerce.intelligent_commerce.review.service.query

import intelligent_commerce.intelligent_commerce.review.repository.ReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReviewQueryService @Autowired constructor(
    private val reviewRepository: ReviewRepository
) {

    fun getReviewById(id: Long) = reviewRepository.findOneDtoById(id)

    fun getReviewByOrder(orderId: Long) = reviewRepository.findOneDtoByOrder(orderId)

    fun getReviewsByItem(lastId: Long?, itemId: Long) = reviewRepository.findOneDtoByItem(lastId, itemId)
}
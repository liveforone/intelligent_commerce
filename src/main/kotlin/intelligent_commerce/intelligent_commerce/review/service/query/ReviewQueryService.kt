package intelligent_commerce.intelligent_commerce.review.service.query

import intelligent_commerce.intelligent_commerce.review.dto.response.ReviewInfo
import intelligent_commerce.intelligent_commerce.review.repository.ReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReviewQueryService @Autowired constructor(
    private val reviewRepository: ReviewRepository
) {

    fun getReviewById(id: Long): ReviewInfo {
        return reviewRepository.findOneDtoById(id)
    }

    fun getReviewByOrder(orderId: Long): ReviewInfo {
        return reviewRepository.findOneDtoByOrder(orderId)
    }

    fun getReviewsByItem(lastId: Long, itemId: Long): List<ReviewInfo> {
        return reviewRepository.findOneDtoByItem(lastId, itemId)
    }
}
package intelligent_commerce.intelligent_commerce.review.repository

import intelligent_commerce.intelligent_commerce.review.domain.Review
import intelligent_commerce.intelligent_commerce.review.dto.response.ReviewInfo

interface ReviewCustomRepository {
    fun findOneByIdJoinMember(id: Long): Review
    fun findOneDtoById(id: Long): ReviewInfo
    fun findOneDtoByOrder(orderId: Long): ReviewInfo
    fun findOneDtoByItem(lastId: Long?, itemId: Long): List<ReviewInfo>
}
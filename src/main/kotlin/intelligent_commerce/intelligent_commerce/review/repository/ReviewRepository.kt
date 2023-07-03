package intelligent_commerce.intelligent_commerce.review.repository

import intelligent_commerce.intelligent_commerce.review.domain.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long>, ReviewCustomRepository {
}
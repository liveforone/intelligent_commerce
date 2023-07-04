package intelligent_commerce.intelligent_commerce.review.domain

import intelligent_commerce.intelligent_commerce.order.domain.Orders
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Review private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(
        unique = true,
        updatable = false
    ) val order: Orders,
    @Column(nullable = false) val content: String,
    @Column(nullable = false, updatable = false) val createdDate: LocalDateTime = LocalDateTime.now()
) {

    companion object {
        fun create(order: Orders, content: String): Review {
            return Review(id = null, order, content)
        }
    }

    fun isOwnerOfReview(identity: String): Boolean{
        return order.member.identity == identity
    }
}
package intelligent_commerce.intelligent_commerce.review.repository

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import intelligent_commerce.intelligent_commerce.exception.exception.ReviewException
import intelligent_commerce.intelligent_commerce.exception.message.ReviewExceptionMessage
import intelligent_commerce.intelligent_commerce.item.domain.Item
import intelligent_commerce.intelligent_commerce.order.domain.Orders
import intelligent_commerce.intelligent_commerce.review.domain.Review
import intelligent_commerce.intelligent_commerce.review.dto.response.ReviewInfo
import intelligent_commerce.intelligent_commerce.review.repository.constant.ReviewRepositoryConstant
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ReviewRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : ReviewCustomRepository {

    override fun findOneByIdJoinMember(id: Long): Review {
        return try {
            queryFactory.singleQuery {
                select(entity(Review::class))
                from(entity(Review::class))
                fetch(Review::order)
                join(Review::order)
                fetch(Orders::member)
                join(Orders::member)
                where(col(Review::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw ReviewException(ReviewExceptionMessage.REVIEW_IS_NULL)
        }
    }

    override fun findOneDtoById(id: Long): ReviewInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    nestedCol(col(Review::order), Orders::id),
                    col(Review::content),
                    col(Review::createdDate)
                ))
                from(entity(Review::class))
                where(col(Review::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw ReviewException(ReviewExceptionMessage.REVIEW_IS_NULL)
        }
    }

    override fun findOneDtoByOrder(orderId: Long): ReviewInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    nestedCol(col(Review::order), Orders::id),
                    col(Review::content),
                    col(Review::createdDate)
                ))
                from(entity(Review::class))
                where(nestedCol(col(Review::order), Orders::id).equal(orderId))
            }
        } catch (e: NoResultException) {
            throw ReviewException(ReviewExceptionMessage.REVIEW_IS_NULL)
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastId(lastId: Long): PredicateSpec? {
        return if (lastId == 0.toLong()) null
        else and(
            col(Review::id).lessThan(lastId)
        )
    }

    override fun findOneDtoByItem(lastId: Long, itemId: Long): List<ReviewInfo> {
        return queryFactory.listQuery {
            select(
                listOf(
                    nestedCol(col(Review::order), Orders::id),
                    col(Review::content),
                    col(Review::createdDate)
                )
            )
            from(entity(Review::class))
            join(Review::order)
            join(Orders::item)
            where(col(Item::id).equal(itemId))
            where(ltLastId(lastId))
            orderBy(col(Review::id).desc())
            limit(ReviewRepositoryConstant.LIMIT_PAGE)
        }
    }
}
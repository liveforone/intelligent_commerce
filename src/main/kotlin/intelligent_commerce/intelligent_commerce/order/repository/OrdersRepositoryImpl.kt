package intelligent_commerce.intelligent_commerce.order.repository

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import intelligent_commerce.intelligent_commerce.exception.exception.OrdersException
import intelligent_commerce.intelligent_commerce.exception.message.OrdersExceptionMessage
import intelligent_commerce.intelligent_commerce.item.domain.Item
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.order.domain.Orders
import intelligent_commerce.intelligent_commerce.order.dto.response.OrderInfo
import intelligent_commerce.intelligent_commerce.order.repository.constant.OrdersRepositoryConstant
import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class OrdersRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : OrdersCustomRepository {

    override fun findOneById(id: Long): Orders {
        return try {
            queryFactory.singleQuery {
                select(entity(Orders::class))
                from(entity(Orders::class))
                fetch(Orders::member)
                join(Orders::member)
                where(col(Orders::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw OrdersException(OrdersExceptionMessage.ORDER_IS_NULL)
        }
    }

    override fun findOneDtoById(id: Long): OrderInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Orders::id),
                    nestedCol(col(Orders::item), Item::id),
                    col(Orders::totalPrice),
                    col(Orders::spentMileage),
                    col(Orders::orderQuantity),
                    col(Orders::orderState),
                    col(Orders::createdDate)
                ))
                from(entity(Orders::class))
                where(col(Orders::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw OrdersException(OrdersExceptionMessage.ORDER_IS_NULL)
        }
    }

    override fun findOneByIdJoinSeller(id: Long): Orders {
        return try {
            queryFactory.singleQuery {
                select(entity(Orders::class))
                from(entity(Orders::class))
                fetch(Orders::member)
                join(Orders::member)
                fetch(Orders::item)
                join(Orders::item)
                fetch(Item::shop)
                join(Item::shop)
                join(Shop::seller)
                where(col(Orders::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw OrdersException(OrdersExceptionMessage.ORDER_IS_NULL)
        }
    }

    override fun findOrdersByIdentity(lastId: Long, identity: String): List<OrderInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Orders::id),
                nestedCol(col(Orders::item), Item::id),
                col(Orders::totalPrice),
                col(Orders::spentMileage),
                col(Orders::orderQuantity),
                col(Orders::orderState),
                col(Orders::createdDate)
            ))
            from(entity(Orders::class))
            where(nestedCol(col(Orders::member), Member::identity).equal(identity))
            where(ltLastId(lastId))
            orderBy(col(Orders::id).desc())
            limit(OrdersRepositoryConstant.LIMIT_PAGE)
        }
    }

    override fun findOrdersBySellerIdentityJoinSeller(lastId: Long, sellerIdentity: String): List<OrderInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Orders::id),
                nestedCol(col(Orders::item), Item::id),
                col(Orders::totalPrice),
                col(Orders::spentMileage),
                col(Orders::orderQuantity),
                col(Orders::orderState),
                col(Orders::createdDate)
            ))
            from(entity(Orders::class))
            join(Orders::item)
            join(Item::shop)
            join(Shop::seller)
            where(col(Member::identity).equal(sellerIdentity))
            where(ltLastId(lastId))
            orderBy(col(Orders::id).desc())
            limit(OrdersRepositoryConstant.LIMIT_PAGE)
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastId(lastId: Long): PredicateSpec? {
        return if (lastId == 0.toLong()) null
        else and(col(Orders::id).lessThan(lastId))
    }
}
package intelligent_commerce.intelligent_commerce.item.repository

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import intelligent_commerce.intelligent_commerce.exception.exception.ItemException
import intelligent_commerce.intelligent_commerce.exception.message.ItemExceptionMessage
import intelligent_commerce.intelligent_commerce.item.domain.Item
import intelligent_commerce.intelligent_commerce.item.dto.response.ItemInfo
import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ItemRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : ItemCustomRepository {

    override fun findOneById(id: Long): Item {
        return try {
            queryFactory.singleQuery {
                select(entity(Item::class))
                from(entity(Item::class))
                where(col(Item::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw ItemException(ItemExceptionMessage.ITEM_IS_NULL)
        }
    }

    override fun findOneByIdJoinShop(id: Long): Item {
        return try {
            queryFactory.singleQuery {
                select(entity(Item::class))
                from(entity(Item::class))
                fetch(Item::shop)
                join(Item::shop)
                where(col(Item::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw ItemException(ItemExceptionMessage.ITEM_IS_NULL)
        }
    }

    override fun findOneDtoById(id: Long): ItemInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Item::id),
                    nestedCol(col(Item::shop), Shop::id),
                    col(Item::title),
                    col(Item::content),
                    col(Item::price),
                    col(Item::remaining)
                ))
                from(entity(Item::class))
                where(col(Item::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw ItemException(ItemExceptionMessage.ITEM_IS_NULL)
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastId(lastId: Long): PredicateSpec? {
        return if (lastId == 0.toLong()) null
        else and(
            col(Item::id).lessThan(lastId)
        )
    }

    override fun findAllItems(lastId: Long): List<ItemInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Item::id),
                nestedCol(col(Item::shop), Shop::id),
                col(Item::title),
                col(Item::content),
                col(Item::price),
                col(Item::remaining)
            ))
            from(entity(Item::class))
            where(ltLastId(lastId))
            orderBy(col(Item::id).desc())
            limit(15)
        }
    }

    override fun findItemsByShopId(shopId: Long, lastId: Long): List<ItemInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Item::id),
                nestedCol(col(Item::shop), Shop::id),
                col(Item::title),
                col(Item::content),
                col(Item::price),
                col(Item::remaining)
            ))
            from(entity(Item::class))
            join(Item::shop)
            where(col(Shop::id).equal(shopId))
            where(ltLastId(lastId))
            orderBy(col(Item::id).desc())
            limit(15)
        }
    }

    override fun searchItemsByKeyword(keyword: String, lastId: Long): List<ItemInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Item::id),
                nestedCol(col(Item::shop), Shop::id),
                col(Item::title),
                col(Item::content),
                col(Item::price),
                col(Item::remaining)
            ))
            from(entity(Item::class))
            where(col(Item::title).like("$keyword%"))
            where(ltLastId(lastId))
            orderBy(col(Item::id).desc())
            limit(15)
        }
    }
}
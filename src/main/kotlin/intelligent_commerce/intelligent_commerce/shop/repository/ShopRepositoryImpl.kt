package intelligent_commerce.intelligent_commerce.shop.repository

import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import intelligent_commerce.intelligent_commerce.exception.exception.ShopException
import intelligent_commerce.intelligent_commerce.exception.message.ShopExceptionMessage
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import intelligent_commerce.intelligent_commerce.shop.dto.response.ShopInfo
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ShopRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : ShopCustomRepository {

    override fun findOneDtoById(id: Long): ShopInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    column(Shop::id),
                    column(Shop::shopName),
                    column(Shop::tel)
                ))
                from(entity(Shop::class))
                where(column(Shop::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw ShopException(ShopExceptionMessage.SHOP_Exception_IS_NULL)
        }
    }

    override fun findOneByIdentity(identity: String): Shop {
        return try {
            queryFactory.singleQuery {
                select(entity(Shop::class))
                from(entity(Shop::class))
                fetch(Shop::seller)
                join(Shop::seller)
                where(column(Member::identity).equal(identity))
            }
        } catch (e: NoResultException) {
            throw ShopException(ShopExceptionMessage.SHOP_Exception_IS_NULL)
        }
    }

    override fun findOneDtoByIdentity(identity: String): ShopInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    column(Shop::id),
                    column(Shop::shopName),
                    column(Shop::tel)
                ))
                from(entity(Shop::class))
                join(Shop::seller)
                where(column(Member::identity).equal(identity))
            }
        } catch (e: NoResultException) {
            throw ShopException(ShopExceptionMessage.SHOP_Exception_IS_NULL)
        }
    }
}
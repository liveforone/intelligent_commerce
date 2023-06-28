package intelligent_commerce.intelligent_commerce.mileage.repository

import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import intelligent_commerce.intelligent_commerce.exception.exception.MileageCustomException
import intelligent_commerce.intelligent_commerce.exception.message.MileageMessage
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.mileage.domain.Mileage
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class MileageRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : MileageCustomRepository {

    override fun findOneByMemberIdentity(identity: String): Mileage {
        return try {
            queryFactory.singleQuery {
                select(entity(Mileage::class))
                from(entity(Mileage::class))
                fetch(Mileage::member)
                join(Mileage::member)
                where(column(Member::identity).equal(identity))
            }
        } catch (e: NoResultException) {
            throw MileageCustomException(MileageMessage.MILEAGE_IS_NULL.message)
        }
    }
}
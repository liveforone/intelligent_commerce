package intelligent_commerce.intelligent_commerce.member.repository

import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import intelligent_commerce.intelligent_commerce.exception.exception.MemberCustomException
import intelligent_commerce.intelligent_commerce.exception.message.MemberMessage
import intelligent_commerce.intelligent_commerce.member.domain.Member
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : MemberCustomRepository {

    override fun findOneById(id: Long): Member {
        return try {
            queryFactory.singleQuery {
                select(entity(Member::class))
                from(entity(Member::class))
                where(column(Member::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw MemberCustomException(MemberMessage.MEMBER_IS_NULL.message)
        }
    }

    override fun findOneByEmail(email: String): Member {
        return try {
            queryFactory.singleQuery {
                select(entity(Member::class))
                from(entity(Member::class))
                where(column(Member::email).equal(email))
            }
        } catch (e: NoResultException) {
            throw MemberCustomException(MemberMessage.MEMBER_IS_NULL.message)
        }
    }

    override fun findOneByIdentity(identity: String): Member {
        return try {
            queryFactory.singleQuery {
                select(entity(Member::class))
                from(entity(Member::class))
                where(column(Member::identity).equal(identity))
            }
        } catch (e: NoResultException) {
            throw MemberCustomException(MemberMessage.MEMBER_IS_NULL.message)
        }
    }
}
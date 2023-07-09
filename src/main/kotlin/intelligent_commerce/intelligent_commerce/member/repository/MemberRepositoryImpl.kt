package intelligent_commerce.intelligent_commerce.member.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import intelligent_commerce.intelligent_commerce.exception.exception.MemberException
import intelligent_commerce.intelligent_commerce.exception.message.MemberExceptionMessage
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.dto.response.MemberInfo
import intelligent_commerce.intelligent_commerce.member.repository.constant.MemberRepositoryConstant
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : MemberCustomRepository {

    override fun findIdByEmailNullableForValidate(email: String): Long? {
        val foundIds = queryFactory.listQuery<Long> {
            select(listOf(col(Member::id)))
            from(entity(Member::class))
            where(col(Member::email).equal(email))
        }
        return if (foundIds.isEmpty()) null else foundIds[MemberRepositoryConstant.FIRST_INDEX]
    }

    override fun findOneById(id: Long): Member {
        return try {
            queryFactory.singleQuery {
                select(entity(Member::class))
                from(entity(Member::class))
                where(col(Member::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw MemberException(MemberExceptionMessage.MEMBER_IS_NULL)
        }
    }

    override fun findOneByEmail(email: String): Member {
        return try {
            queryFactory.singleQuery {
                select(entity(Member::class))
                from(entity(Member::class))
                where(col(Member::email).equal(email))
            }
        } catch (e: NoResultException) {
            throw MemberException(MemberExceptionMessage.MEMBER_IS_NULL)
        }
    }

    override fun findOneByIdentity(identity: String): Member {
        return try {
            queryFactory.singleQuery {
                select(entity(Member::class))
                from(entity(Member::class))
                where(col(Member::identity).equal(identity))
            }
        } catch (e: NoResultException) {
            throw MemberException(MemberExceptionMessage.MEMBER_IS_NULL)
        }
    }

    override fun findOneDtoByIdentity(identity: String): MemberInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Member::id),
                    col(Member::email),
                    col(Member::bankbookNum),
                    col(Member::auth),
                    col(Member::address)
                ))
                from(entity(Member::class))
                where(col(Member::identity).equal(identity))
            }
        } catch (e: NoResultException) {
            throw MemberException(MemberExceptionMessage.MEMBER_IS_NULL)
        }
    }
}
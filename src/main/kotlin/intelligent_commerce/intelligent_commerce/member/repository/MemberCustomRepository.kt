package intelligent_commerce.intelligent_commerce.member.repository

import intelligent_commerce.intelligent_commerce.member.domain.Member

interface MemberCustomRepository {

    fun findOneById(id: Long): Member
    fun findOneByEmail(email: String): Member
    fun findOneByIdentity(identity: String): Member
}
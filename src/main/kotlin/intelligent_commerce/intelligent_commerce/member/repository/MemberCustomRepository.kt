package intelligent_commerce.intelligent_commerce.member.repository

import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.dto.response.MemberInfo

interface MemberCustomRepository {

    fun findOneById(id: Long): Member
    fun findOneByEmail(email: String): Member
    fun findOneByIdentity(identity: String): Member
    fun findOneDtoByIdentity(identity: String): MemberInfo
}
package intelligent_commerce.intelligent_commerce.member.dto.response

import intelligent_commerce.intelligent_commerce.member.domain.Address
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.domain.Role

data class MemberInfo(
    val id: Long?,
    val bankbookNum: String,
    val auth: Role,
    val address: Address
) {
    companion object {
        fun entityToDto(member: Member): MemberInfo {
            return MemberInfo(member.id, member.bankbookNum, member.auth, member.address)
        }
    }
}

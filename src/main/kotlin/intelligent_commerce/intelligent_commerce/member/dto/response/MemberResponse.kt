package intelligent_commerce.intelligent_commerce.member.dto.response

import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.domain.Role

data class MemberResponse(
    val id: Long?,
    val bankbookNum: String,
    val auth: Role
) {
    companion object {
        fun entityToDto(member: Member): MemberResponse {
            return MemberResponse(member.id, member.bankbookNum, member.auth)
        }
    }
}

package intelligent_commerce.intelligent_commerce.member.dto.response

import intelligent_commerce.intelligent_commerce.member.domain.Address
import intelligent_commerce.intelligent_commerce.member.domain.Role

data class MemberInfo(
    val id: Long?,
    val email: String,
    val bankbookNum: String,
    val auth: Role,
    val address: Address
)

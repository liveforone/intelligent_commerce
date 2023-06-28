package intelligent_commerce.intelligent_commerce.member.domain

import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    val city: String,
    val roadNum: String,
    val detail: String
)
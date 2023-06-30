package intelligent_commerce.intelligent_commerce.member.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    @Column(nullable = false)
    val city: String,
    @Column(nullable = false)
    val roadNum: String,
    @Column(nullable = false)
    val detail: String
)
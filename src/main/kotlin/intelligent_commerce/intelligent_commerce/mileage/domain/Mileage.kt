package intelligent_commerce.intelligent_commerce.mileage.domain

import intelligent_commerce.intelligent_commerce.member.domain.Member
import jakarta.persistence.*

@Entity
class Mileage private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = "member_identity",
        referencedColumnName = "identity"
    ) val member: Member,
    var mileagePoint: Long
) {
    companion object {
        fun create(member: Member): Mileage = Mileage(null, member, 0)
    }

    fun addPoint(itemPrice: Long) {
        val calculatedMileage = MileagePolicy.calculateMileage(itemPrice)
        this.mileagePoint += calculatedMileage
    }

    fun rollbackPoint(itemPrice: Long) {
        val calculatedMileage = MileagePolicy.calculateMileage(itemPrice)
        this.mileagePoint -= calculatedMileage
    }

    fun subtractPoint(pointToUse: Long) {
        this.mileagePoint -= pointToUse
    }
}
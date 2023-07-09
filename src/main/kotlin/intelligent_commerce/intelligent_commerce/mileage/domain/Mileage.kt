package intelligent_commerce.intelligent_commerce.mileage.domain

import intelligent_commerce.intelligent_commerce.exception.exception.MileageException
import intelligent_commerce.intelligent_commerce.exception.message.MileageExceptionMessage
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.mileage.domain.constant.MileageConstant
import jakarta.persistence.*

@Entity
class Mileage private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(
        name = MileageConstant.MEMBER_COLUMN_NAME,
        referencedColumnName = MileageConstant.IDENTITY,
        updatable = false,
        unique = true
    ) val member: Member,
    @Column(nullable = false) var mileagePoint: Long
) {
    companion object {
        fun create(member: Member) = Mileage(id = null, member, MileageConstant.DEFAULT_MILEAGE_POINT)
    }

    fun addPoint(totalItemPrice: Long) {
        val calculatedMileage = MileagePolicy.calculateMileage(totalItemPrice)
        mileagePoint += calculatedMileage
    }

    fun rollbackAddPoint(totalItemPrice: Long) {
        val calculatedMileage = MileagePolicy.calculateMileage(totalItemPrice)
        check(mileagePoint - calculatedMileage >= MileageConstant.MILEAGE_MINIMUM) { throw MileageException(MileageExceptionMessage.MILEAGE_ALREADY_USE) }
        mileagePoint -= calculatedMileage
    }

    fun subtractPoint(pointToUse: Long) {
        require(mileagePoint - pointToUse >= MileageConstant.MILEAGE_MINIMUM) { throw MileageException(MileageExceptionMessage.POINT_TO_USE_IS_OVER) }
        check(mileagePoint >= MileageConstant.USE_MILEAGE_LIMIT_POINT) { throw MileageException(MileageExceptionMessage.MILEAGE_IS_TOO_SMALL) }
        mileagePoint -= pointToUse
    }

    fun rollbackSubtractPoint(pointToUse: Long) {
        this.mileagePoint += pointToUse
    }
}
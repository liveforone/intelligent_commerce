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
        referencedColumnName = MileageConstant.IDENTITY
    ) val member: Member,
    @Column(nullable = false) var mileagePoint: Long
) {
    companion object {
        fun create(member: Member): Mileage = Mileage(null, member, 0)
    }

    fun addPoint(itemPrice: Long) {
        val calculatedMileage = MileagePolicy.calculateMileage(itemPrice)
        mileagePoint += calculatedMileage
    }

    fun rollbackAddPoint(itemPrice: Long) {
        val calculatedMileage = MileagePolicy.calculateMileage(itemPrice)
        if (mileagePoint - calculatedMileage < 0) throw MileageException(MileageExceptionMessage.MILEAGE_ALREADY_USE)
        mileagePoint -= calculatedMileage
    }

    fun subtractPoint(pointToUse: Long) {
        if (mileagePoint - pointToUse < 0) throw MileageException(MileageExceptionMessage.POINT_TO_USE_IS_OVER)
        if (mileagePoint < MileageConstant.USE_MILEAGE_LIMIT_POINT) throw MileageException(MileageExceptionMessage.MILEAGE_IS_TOO_SMALL)
        mileagePoint -= pointToUse
    }

    fun rollbackSubtractPoint(pointToUse: Long) {
        this.mileagePoint += pointToUse
    }
}
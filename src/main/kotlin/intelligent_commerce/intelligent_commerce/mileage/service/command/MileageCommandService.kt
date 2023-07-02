package intelligent_commerce.intelligent_commerce.mileage.service.command

import intelligent_commerce.intelligent_commerce.member.repository.MemberRepository
import intelligent_commerce.intelligent_commerce.mileage.domain.Mileage
import intelligent_commerce.intelligent_commerce.mileage.repository.MileageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MileageCommandService @Autowired constructor(
    private val mileageRepository: MileageRepository,
    private val memberRepository: MemberRepository
) {

    fun createMileage(identity: String) {
        Mileage.create(memberRepository.findOneByIdentity(identity))
            .also {
                mileageRepository.save(it)
            }
    }

    fun addPoint(itemPrice: Long, identity: String) {
        mileageRepository.findOneByIdentity(identity)
            .also {
                it.addPoint(itemPrice)
            }
    }

    fun subtractPoint(pointToUse: Long, identity: String) {
        mileageRepository.findOneByIdentity(identity)
            .also {
                it.subtractPoint(pointToUse)
            }
    }

    fun rollbackMileage(pointToUse: Long, itemPrice: Long, identity: String) {
        mileageRepository.findOneByIdentity(identity)
            .also {
                it.rollbackSubtractPoint(pointToUse)
                it.rollbackAddPoint(itemPrice)
            }
    }

    fun deleteMileage(identity: String) {
        mileageRepository.findOneByIdentity(identity)
            .also {
                mileageRepository.delete(it)
            }
    }
}
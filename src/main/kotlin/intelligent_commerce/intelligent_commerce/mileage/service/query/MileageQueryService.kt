package intelligent_commerce.intelligent_commerce.mileage.service.query

import intelligent_commerce.intelligent_commerce.mileage.dto.MileageResponse
import intelligent_commerce.intelligent_commerce.mileage.repository.MileageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MileageQueryService @Autowired constructor(
    private val mileageRepository: MileageRepository
) {

    fun getMileageByMemberIdentity(identity: String): MileageResponse {
        return MileageResponse.entityToDto(mileageRepository.findOneByMemberIdentity(identity))
    }
}
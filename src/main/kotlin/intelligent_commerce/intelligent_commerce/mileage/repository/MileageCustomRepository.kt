package intelligent_commerce.intelligent_commerce.mileage.repository

import intelligent_commerce.intelligent_commerce.mileage.domain.Mileage
import intelligent_commerce.intelligent_commerce.mileage.dto.MileageInfo

interface MileageCustomRepository {

    fun findOneByIdentity(identity: String): Mileage
    fun findOneDtoByIdentity(identity: String): MileageInfo
}
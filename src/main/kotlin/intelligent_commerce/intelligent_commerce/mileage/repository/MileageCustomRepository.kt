package intelligent_commerce.intelligent_commerce.mileage.repository

import intelligent_commerce.intelligent_commerce.mileage.domain.Mileage

interface MileageCustomRepository {

    fun findOneByMemberIdentity(identity: String): Mileage
}
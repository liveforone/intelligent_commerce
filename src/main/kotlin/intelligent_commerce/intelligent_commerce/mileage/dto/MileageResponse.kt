package intelligent_commerce.intelligent_commerce.mileage.dto

import intelligent_commerce.intelligent_commerce.mileage.domain.Mileage

data class MileageResponse(
    val id: Long?,
    val mileagePoint: Long
) {
    companion object {
        fun entityToDto(mileage: Mileage): MileageResponse {
            return MileageResponse(mileage.id, mileage.mileagePoint)
        }
    }
}
package intelligent_commerce.intelligent_commerce.exception.message

enum class MileageExceptionMessage(val status: Int, val message: String) {
    MILEAGE_IS_NULL(404, "마일리지가 존재하지 않습니다.")
}
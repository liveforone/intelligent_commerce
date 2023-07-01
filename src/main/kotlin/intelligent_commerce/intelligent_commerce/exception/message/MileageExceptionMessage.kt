package intelligent_commerce.intelligent_commerce.exception.message

enum class MileageExceptionMessage(val status: Int, val message: String) {
    MILEAGE_IS_NULL(404, "마일리지가 존재하지 않습니다."),
    MILEAGE_ALREADY_USE(400, "마일리지를 이미 사용하여, 롤백이 불가능합니다."),
    POINT_TO_USE_IS_OVER(400, "사용 가능 마일리지를 초과하였습니다."),
    MILEAGE_IS_TOO_SMALL(400, "5000 이하의 마일리지는 사용불가능합니다.")
}
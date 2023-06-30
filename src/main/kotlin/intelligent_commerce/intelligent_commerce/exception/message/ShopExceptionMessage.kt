package intelligent_commerce.intelligent_commerce.exception.message

enum class ShopExceptionMessage(val status: Int, val message: String) {
    SHOP_IS_NULL(404, "상점이 존재하지 않습니다."), AUTH_IS_NOT_SELLER(403, "접근 불가능한 권한입니다.")
}
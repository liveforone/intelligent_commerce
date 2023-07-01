package intelligent_commerce.intelligent_commerce.exception.message

enum class ItemExceptionMessage(val status: Int, val message: String) {
    ITEM_IS_NULL(404, "상품이 존재하지 않습니다."),
    REMAINING_IS_ZERO(400, "재고가 모두 소진되었습니다."),
    NOT_OWNER_OF_ITEM(403, "상품의 등록자가 아닙니다.")
}
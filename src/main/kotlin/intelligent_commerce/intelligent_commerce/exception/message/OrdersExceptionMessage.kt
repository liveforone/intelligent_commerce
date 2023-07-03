package intelligent_commerce.intelligent_commerce.exception.message

enum class OrdersExceptionMessage(val status: Int, val message: String) {
    ORDER_IS_NULL(404, "주문이 존재하지 않습니다."),
    NOT_OWNER_OF_ITEM(403, "상품을 등록한 판매자가 아닙니다."),
    NOT_OWNER_OF_ORDER(403, "주문자가 아닙니다."),
    ALREADY_CANCELED(400, "이미 취소된 주문입니다."),
    ALREADY_DELIVERY_COMPLETED(400, "이미 배송 완료되어 취소가 불가능합니다."),
    OVER_CANCEL_LIMIT_DAY(400, "주문 취소 날짜를 초과하여 주문취소가 불가능합니다.")
}
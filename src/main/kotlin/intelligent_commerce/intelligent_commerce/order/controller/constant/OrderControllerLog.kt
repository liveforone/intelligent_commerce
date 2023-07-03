package intelligent_commerce.intelligent_commerce.order.controller.constant

enum class OrderControllerLog(val log: String) {
    ORDER_SUCCESS("주문 성공"),
    DELIVERY_COMPLETED_SUCCESS("배송상태 -> 배송완료 변경 성공"),
    ORDER_CANCEL_SUCCESS("주문 취소 성공")
}
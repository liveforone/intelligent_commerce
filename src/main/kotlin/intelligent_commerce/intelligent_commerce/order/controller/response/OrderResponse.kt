package intelligent_commerce.intelligent_commerce.order.controller.response

import intelligent_commerce.intelligent_commerce.order.dto.response.OrderInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object OrderResponse {

    private const val ORDER_SUCCESS = "주문을 성공적으로 완료했습니다."
    private const val DELIVERY_COMPLETED_SUCCESS = "배송준비 주문을 성공적으로 배송완료 처리하였습니다."
    private const val ORDER_CANCEL_SUCCESS = "주문 취소를 성공적으로 완료했습니다."

    fun orderDetailSuccess(orderInfo: OrderInfo): ResponseEntity<OrderInfo> = ResponseEntity.ok(orderInfo)

    fun myOrderSuccess(orderInfo: List<OrderInfo>): ResponseEntity<List<OrderInfo>> {
        return ResponseEntity.ok(orderInfo)
    }

    fun shopOrderListSuccess(orderInfo: List<OrderInfo>): ResponseEntity<List<OrderInfo>> {
        return ResponseEntity.ok(orderInfo)
    }

    fun orderSuccess(): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ORDER_SUCCESS)
    }

    fun deliveryCompletedSuccess(): ResponseEntity<String> = ResponseEntity.ok(DELIVERY_COMPLETED_SUCCESS)

    fun orderCancelSuccess(): ResponseEntity<String> = ResponseEntity.ok(ORDER_CANCEL_SUCCESS)
}
package intelligent_commerce.intelligent_commerce.order.controller

import intelligent_commerce.intelligent_commerce.logger
import intelligent_commerce.intelligent_commerce.order.controller.constant.OrderControllerLog
import intelligent_commerce.intelligent_commerce.order.controller.constant.OrderParam
import intelligent_commerce.intelligent_commerce.order.controller.constant.OrderUrl
import intelligent_commerce.intelligent_commerce.order.controller.response.OrderResponse
import intelligent_commerce.intelligent_commerce.order.dto.request.CreateOrder
import intelligent_commerce.intelligent_commerce.order.service.command.OrdersCommandService
import intelligent_commerce.intelligent_commerce.order.service.query.OrdersQueryService
import intelligent_commerce.intelligent_commerce.validator.ControllerValidator
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class OrderController @Autowired constructor(
    private val ordersCommandService: OrdersCommandService,
    private val ordersQueryService: OrdersQueryService,
    private val controllerValidator: ControllerValidator
) {

    @GetMapping(OrderUrl.ORDER_DETAIL)
    fun orderDetail(@PathVariable(OrderParam.ID) id: Long): ResponseEntity<*> {
        val orderDetail = ordersQueryService.getOrderById(id)
        return OrderResponse.orderDetailSuccess(orderDetail)
    }

    @GetMapping(OrderUrl.MY_ORDER)
    fun myOrder(
        @RequestParam(OrderParam.LAST_ID, defaultValue = OrderParam.DEFAULT_LAST_ID) lastId: Long,
        principal: Principal
    ): ResponseEntity<*> {
        val orders = ordersQueryService.getOrdersByIdentity(lastId, identity = principal.name)
        return OrderResponse.myOrderSuccess(orders)
    }

    @GetMapping(OrderUrl.SHOP_ORDER_LIST)
    fun shopOrderList(
        @RequestParam(OrderParam.LAST_ID, defaultValue = OrderParam.DEFAULT_LAST_ID) lastId: Long,
        principal: Principal
    ): ResponseEntity<*> {
        val orders = ordersQueryService.getOrdersBySeller(lastId, sellerIdentity = principal.name)
        return OrderResponse.shopOrderListSuccess(orders)
    }

    @PostMapping(OrderUrl.ORDER)
    fun order(
        @RequestBody @Valid createOrder: CreateOrder,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        ordersCommandService.createOrder(createOrder, identity = principal.name)
        logger().info(OrderControllerLog.ORDER_SUCCESS.log)

        return OrderResponse.orderSuccess()
    }

    @PutMapping(OrderUrl.DELIVERY_COMPLETED)
    fun deliveryCompleted(
        @PathVariable(OrderParam.ID) id: Long,
        principal: Principal
    ): ResponseEntity<*> {
        ordersCommandService.deliveryCompleted(id, sellerIdentity = principal.name)
        logger().info(OrderControllerLog.DELIVERY_COMPLETED_SUCCESS.log)

        return OrderResponse.deliveryCompletedSuccess()
    }

    @PutMapping(OrderUrl.ORDER_CANCEL)
    fun orderCancel(
        @PathVariable(OrderParam.ID) id: Long,
        principal: Principal
    ): ResponseEntity<*> {
        ordersCommandService.cancelOrderByMember(id, identity = principal.name)
        logger().info(OrderControllerLog.ORDER_CANCEL_SUCCESS.log)

        return OrderResponse.orderCancelSuccess()
    }
}
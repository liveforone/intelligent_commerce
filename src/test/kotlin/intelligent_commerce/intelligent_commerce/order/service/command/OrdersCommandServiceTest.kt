package intelligent_commerce.intelligent_commerce.order.service.command

import intelligent_commerce.intelligent_commerce.item.dto.request.CreateItem
import intelligent_commerce.intelligent_commerce.item.service.command.ItemCommandService
import intelligent_commerce.intelligent_commerce.member.dto.request.SignupRequest
import intelligent_commerce.intelligent_commerce.member.service.command.MemberCommandService
import intelligent_commerce.intelligent_commerce.order.domain.OrderState
import intelligent_commerce.intelligent_commerce.order.dto.request.CreateOrder
import intelligent_commerce.intelligent_commerce.order.service.query.OrdersQueryService
import intelligent_commerce.intelligent_commerce.shop.dto.request.CreateShop
import intelligent_commerce.intelligent_commerce.shop.service.command.ShopCommandService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class OrdersCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val shopCommandService: ShopCommandService,
    private val itemCommandService: ItemCommandService,
    private val ordersCommandService: OrdersCommandService,
    private val ordersQueryService: OrdersQueryService
) {

    fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    private fun createMember(): String {
        val signupRequest = SignupRequest("test_member@gmail.com", "1111", "9876543212345", "서울", "강남-1-1", "101동 102호")
        val identity = memberCommandService.createMember(signupRequest)
        flushAndClear()
        return identity
    }

    private fun createSeller(): String {
        val signupRequest = SignupRequest("test_seller@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()
        return identity
    }

    private fun createItem(identity: String): Long {
        val createShop = CreateShop("test_shop", "0212345678")
        shopCommandService.createShop(createShop, identity)
        flushAndClear()
        val createItem = CreateItem("test_title", "test_content", 500000, 2500 , 10)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()
        return itemId
    }

    @Test
    @Transactional
    fun createOrderTest() {
        //given
        val sellerIdentity = createSeller()
        val itemId = createItem(sellerIdentity)
        val memberIdentity = createMember()
        val requestDto = CreateOrder(itemId, null, null)

        //when
        val orderId = ordersCommandService.createOrder(requestDto, memberIdentity)
        flushAndClear()

        //then
        Assertions.assertThat(ordersQueryService.getOrderById(orderId)).isNotNull
    }

    @Test
    @Transactional
    fun deliveryCompletedTest() {
        //given
        val sellerIdentity = createSeller()
        val itemId = createItem(sellerIdentity)
        val memberIdentity = createMember()
        val requestDto = CreateOrder(itemId, null, null)
        val orderId = ordersCommandService.createOrder(requestDto, memberIdentity)
        flushAndClear()

        //when
        ordersCommandService.deliveryCompleted(orderId, sellerIdentity)
        flushAndClear()

        //then
        Assertions.assertThat(ordersQueryService.getOrderById(orderId).orderState)
            .isEqualTo(OrderState.DELIVERY_COMPLETED)
    }

    @Test
    @Transactional
    fun cancelOrderTest() {
        //given
        val sellerIdentity = createSeller()
        val itemId = createItem(sellerIdentity)
        val memberIdentity = createMember()
        //마일리지를 적립하기위한 주문
        val orderForEarnMileage = CreateOrder(itemId, null, null)
        ordersCommandService.createOrder(orderForEarnMileage, memberIdentity)
        //테스트 할 주문
        val requestDto = CreateOrder(itemId, 1000, 2)
        val orderId = ordersCommandService.createOrder(requestDto, memberIdentity)
        flushAndClear()

        //when
        ordersCommandService.cancelOrderByMember(orderId, memberIdentity)
        flushAndClear()

        //then
        Assertions.assertThat(ordersQueryService.getOrderById(orderId).orderState)
            .isEqualTo(OrderState.CANCEL)
    }
}
package intelligent_commerce.intelligent_commerce.review.service.command

import intelligent_commerce.intelligent_commerce.item.dto.request.CreateItem
import intelligent_commerce.intelligent_commerce.item.service.command.ItemCommandService
import intelligent_commerce.intelligent_commerce.member.dto.request.SignupRequest
import intelligent_commerce.intelligent_commerce.member.service.command.MemberCommandService
import intelligent_commerce.intelligent_commerce.order.dto.request.CreateOrder
import intelligent_commerce.intelligent_commerce.order.service.command.OrdersCommandService
import intelligent_commerce.intelligent_commerce.review.dto.request.CreateReview
import intelligent_commerce.intelligent_commerce.review.service.query.ReviewQueryService
import intelligent_commerce.intelligent_commerce.shop.dto.request.CreateShop
import intelligent_commerce.intelligent_commerce.shop.service.command.ShopCommandService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class ReviewCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val shopCommandService: ShopCommandService,
    private val itemCommandService: ItemCommandService,
    private val ordersCommandService: OrdersCommandService,
    private val reviewCommandService: ReviewCommandService,
    private val reviewQueryService: ReviewQueryService
) {

    fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    private fun createOrder(): Long {
        val sellerSignupRequest = SignupRequest("test_seller@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val sellerIdentity = memberCommandService.createSeller(sellerSignupRequest)
        flushAndClear()
        val createShop = CreateShop("test_shop", "0212345678")
        shopCommandService.createShop(createShop, sellerIdentity)
        flushAndClear()
        val createItem = CreateItem("test_title", "test_content", 500000, 2500, 10)
        val itemId = itemCommandService.createItem(createItem, sellerIdentity)
        flushAndClear()
        val memberSignupRequest = SignupRequest("test_member@gmail.com", "1111", "9876543212345", "서울", "강남-1-1", "101동 102호")
        val memberIdentity = memberCommandService.createMember(memberSignupRequest)
        flushAndClear()
        val requestDto = CreateOrder(itemId, null, null)
        val orderId = ordersCommandService.createOrder(requestDto, memberIdentity)
        flushAndClear()
        return orderId
    }

    @Test
    @Transactional
    fun createReview() {
        //given
        val content = "test_review"
        val orderId = createOrder()

        //when
        val request = CreateReview(orderId, content)
        reviewCommandService.createReview(request)
        flushAndClear()

        //then
        Assertions.assertThat(reviewQueryService.getReviewByOrder(orderId).content)
            .isEqualTo(content)
    }
}
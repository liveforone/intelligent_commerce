package intelligent_commerce.intelligent_commerce.item.service.command

import intelligent_commerce.intelligent_commerce.item.dto.request.CreateItem
import intelligent_commerce.intelligent_commerce.item.dto.update.*
import intelligent_commerce.intelligent_commerce.item.service.query.ItemQueryService
import intelligent_commerce.intelligent_commerce.member.dto.request.SignupRequest
import intelligent_commerce.intelligent_commerce.member.service.command.MemberCommandService
import intelligent_commerce.intelligent_commerce.shop.dto.request.CreateShop
import intelligent_commerce.intelligent_commerce.shop.service.command.ShopCommandService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class ItemCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val shopCommandService: ShopCommandService,
    private val itemCommandService: ItemCommandService,
    private val itemQueryService: ItemQueryService
) {

    fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun createItem() {
        //given
        val signupRequest = SignupRequest("shop_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()
        val createShop = CreateShop("test_shop", "0212345678")
        shopCommandService.createShop(createShop, identity)
        flushAndClear()

        //when
        val createItem = CreateItem("test_title1", "test_content1", 20000, 2500 , 10)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //then
        Assertions.assertThat(itemQueryService.getItemById(itemId))
            .isNotNull
    }

    @Test
    @Transactional
    fun updateTitle() {
        //given
        val signupRequest = SignupRequest("shop_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()
        val createShop = CreateShop("test_shop", "0212345678")
        shopCommandService.createShop(createShop, identity)
        flushAndClear()
        val createItem = CreateItem("test_title2", "test_content2", 20000, 2500 , 10)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //when
        val updatedTitle = "update_title"
        val request = UpdateItemTitle(itemId, updatedTitle)
        itemCommandService.updateTitle(request, identity)
        flushAndClear()

        //then
        Assertions.assertThat(itemQueryService.getItemById(itemId).title)
            .isEqualTo(updatedTitle)
    }

    @Test
    @Transactional
    fun updateContent() {
        //given
        val signupRequest = SignupRequest("shop_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()
        val createShop = CreateShop("test_shop", "0212345678")
        shopCommandService.createShop(createShop, identity)
        flushAndClear()
        val createItem = CreateItem("test_title3", "test_content3", 20000, 2500 , 10)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //when
        val updatedContent = "updated_content"
        val request = UpdateItemContent(itemId, updatedContent)
        itemCommandService.updateContent(request, identity)
        flushAndClear()

        //then
        Assertions.assertThat(itemQueryService.getItemById(itemId).content)
            .isEqualTo(updatedContent)
    }

    @Test
    @Transactional
    fun updatePrice() {
        //given
        val signupRequest = SignupRequest("shop_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()
        val createShop = CreateShop("test_shop", "0212345678")
        shopCommandService.createShop(createShop, identity)
        flushAndClear()
        val createItem = CreateItem("test_title4", "test_content4", 20000, 2500 , 10)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //when
        val updatedPrice: Long = 50000
        val request = UpdatePrice(itemId, updatedPrice)
        itemCommandService.updatePrice(request, identity)
        flushAndClear()

        //then
        Assertions.assertThat(itemQueryService.getItemById(itemId).price)
            .isEqualTo(updatedPrice)
    }

    @Test
    @Transactional
    fun updateDeliveryCharge() {
        //given
        val signupRequest = SignupRequest("shop_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()
        val createShop = CreateShop("test_shop", "0212345678")
        shopCommandService.createShop(createShop, identity)
        flushAndClear()
        val createItem = CreateItem("test_title5", "test_content5", 20000, 2500 , 10)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //when
        val updatedDeliveryCharge: Long = 5000
        val request = UpdateDeliveryCharge(itemId, updatedDeliveryCharge)
        itemCommandService.updateDeliverCharge(request, identity)
        flushAndClear()

        //then
        Assertions.assertThat(itemQueryService.getItemById(itemId).deliveryCharge)
            .isEqualTo(updatedDeliveryCharge)
    }

    @Test
    @Transactional
    fun addRemaining() {
        //given
        val signupRequest = SignupRequest("shop_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()
        val createShop = CreateShop("test_shop", "0212345678")
        shopCommandService.createShop(createShop, identity)
        flushAndClear()
        val remaining: Long = 10
        val createItem = CreateItem("test_title6", "test_content6", 20000, 2500 , 10)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //when
        val updatedRemaining: Long = 30
        val request = AddRemaining(itemId, updatedRemaining)
        itemCommandService.addRemaining(request, identity)
        flushAndClear()

        //then
        Assertions.assertThat(itemQueryService.getItemById(itemId).remaining)
            .isEqualTo(remaining + updatedRemaining)
    }

    @Test
    @Transactional
    fun minusRemaining() {
        //given
        val signupRequest = SignupRequest("shop_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()
        val createShop = CreateShop("test_shop", "0212345678")
        shopCommandService.createShop(createShop, identity)
        flushAndClear()
        val remaining: Long = 10
        val createItem = CreateItem("test_title7", "test_content7", 20000, 2500 , 10)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //when
        val orderQuantity: Long = 2
        itemCommandService.minusRemaining(orderQuantity, itemId)
        flushAndClear()

        //then
        Assertions.assertThat(itemQueryService.getItemById(itemId).remaining)
            .isEqualTo(remaining - orderQuantity)
    }

    @Test
    @Transactional
    fun rollbackMinusRemaining() {
        //given
        val signupRequest = SignupRequest("shop_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()
        val createShop = CreateShop("test_shop", "0212345678")
        shopCommandService.createShop(createShop, identity)
        flushAndClear()
        val remaining: Long = 10
        val createItem = CreateItem("test_title8", "test_content8", 20000, 2500 , 10)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()
        val orderQuantity: Long = 2
        itemCommandService.minusRemaining(orderQuantity, itemId)
        flushAndClear()

        //when
        itemCommandService.rollbackMinusRemaining(orderQuantity, itemId)
        flushAndClear()

        //then
        Assertions.assertThat(itemQueryService.getItemById(itemId).remaining)
            .isEqualTo(remaining)
    }
}
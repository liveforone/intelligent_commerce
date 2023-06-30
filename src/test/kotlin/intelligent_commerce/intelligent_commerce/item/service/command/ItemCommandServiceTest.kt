package intelligent_commerce.intelligent_commerce.item.service.command

import intelligent_commerce.intelligent_commerce.item.dto.request.CreateItem
import intelligent_commerce.intelligent_commerce.item.dto.update.AddRemaining
import intelligent_commerce.intelligent_commerce.item.dto.update.UpdateItemContent
import intelligent_commerce.intelligent_commerce.item.dto.update.UpdateItemTitle
import intelligent_commerce.intelligent_commerce.item.dto.update.UpdatePrice
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
        val createItem = CreateItem("test_title", "test_content", 20000u, 10u)
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
        val createItem = CreateItem("test_title", "test_content", 20000u, 10u)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //when
        val updatedTitle = "update_title"
        val request = UpdateItemTitle(updatedTitle)
        itemCommandService.updateTitle(request, itemId)
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
        val createItem = CreateItem("test_title", "test_content", 20000u, 10u)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //when
        val updatedContent = "updated_content"
        val request = UpdateItemContent(updatedContent)
        itemCommandService.updateContent(request, itemId)
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
        val createItem = CreateItem("test_title", "test_content", 20000u, 10u)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //when
        val updatedPrice: ULong = 50000u
        val request = UpdatePrice(updatedPrice)
        itemCommandService.updatePrice(request, itemId)
        flushAndClear()

        //then
        Assertions.assertThat(itemQueryService.getItemById(itemId).price)
            .isEqualTo(updatedPrice)
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
        val remaining: ULong = 10u
        val createItem = CreateItem("test_title", "test_content", 20000u, remaining)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //when
        val updatedRemaining: ULong = 30u
        val request = AddRemaining(updatedRemaining)
        itemCommandService.addRemaining(request, itemId)
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
        val remaining: ULong = 10u
        val createItem = CreateItem("test_title", "test_content", 20000u, remaining)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()

        //when
        itemCommandService.minusRemaining(itemId)
        flushAndClear()

        //then
        Assertions.assertThat(itemQueryService.getItemById(itemId).remaining)
            .isEqualTo(remaining - 1u)
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
        val remaining: ULong = 10u
        val createItem = CreateItem("test_title", "test_content", 20000u, remaining)
        val itemId = itemCommandService.createItem(createItem, identity)
        flushAndClear()
        itemCommandService.minusRemaining(itemId)
        flushAndClear()

        //when
        itemCommandService.rollbackMinusRemaining(itemId)
        flushAndClear()

        //then
        Assertions.assertThat(itemQueryService.getItemById(itemId).remaining)
            .isEqualTo(remaining)
    }
}
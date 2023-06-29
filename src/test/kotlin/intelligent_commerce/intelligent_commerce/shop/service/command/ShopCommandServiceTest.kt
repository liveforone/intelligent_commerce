package intelligent_commerce.intelligent_commerce.shop.service.command

import intelligent_commerce.intelligent_commerce.member.dto.request.SignupRequest
import intelligent_commerce.intelligent_commerce.member.service.command.MemberCommandService
import intelligent_commerce.intelligent_commerce.shop.dto.request.CreateShop
import intelligent_commerce.intelligent_commerce.shop.dto.update.UpdateShopName
import intelligent_commerce.intelligent_commerce.shop.dto.update.UpdateTel
import intelligent_commerce.intelligent_commerce.shop.service.query.ShopQueryService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class ShopCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val shopCommandService: ShopCommandService,
    private val shopQueryService: ShopQueryService,
    private val memberCommandService: MemberCommandService
) {

    fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun createShop() {
        //given
        val signupRequest = SignupRequest("shop_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()

        //when
        val request = CreateShop("test_shop", "0212345678")
        shopCommandService.createShop(request, identity)
        flushAndClear()

        //then
        Assertions.assertThat(shopQueryService.getShopByIdentity(identity))
            .isNotNull
    }

    @Test
    @Transactional
    fun updateShopName() {
        //given
        val signupRequest = SignupRequest("shop_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()
        val shopName = "test_shop"
        val tel = "0212345678"
        val createShop = CreateShop(shopName, tel)
        shopCommandService.createShop(createShop, identity)
        flushAndClear()

        //when
        val updatedShopName = "updated_shopName"
        val request = UpdateShopName(updatedShopName)
        shopCommandService.updateShopName(request, identity)
        flushAndClear()

        //then
        Assertions.assertThat(shopQueryService.getShopByIdentity(identity).shopName)
            .isEqualTo(updatedShopName)
    }

    @Test
    @Transactional
    fun updateTel() {
        //given
        val signupRequest = SignupRequest("shop_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createSeller(signupRequest)
        flushAndClear()
        val shopName = "test_shop"
        val tel = "0212345678"
        val createShop = CreateShop(shopName, tel)
        shopCommandService.createShop(createShop, identity)
        flushAndClear()

        //when
        val updatedTel = "01098765432"
        val request = UpdateTel(updatedTel)
        shopCommandService.updateTel(request, identity)
        flushAndClear()

        //then
        Assertions.assertThat(shopQueryService.getShopByIdentity(identity).tel)
            .isEqualTo(updatedTel)
    }
}
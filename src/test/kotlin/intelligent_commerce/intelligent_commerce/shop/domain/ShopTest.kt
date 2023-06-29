package intelligent_commerce.intelligent_commerce.shop.domain

import intelligent_commerce.intelligent_commerce.member.domain.Address
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.domain.Role
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ShopTest {

    @Test
    fun updateShopNameTest() {
        //given
        val seller = Member.create("test@gmail.com", "1234", "1234567898765", Role.MEMBER, Address("seoul", "잠실-1-1", "102동 102호"))
        val shopName = "test_shop"
        val tel = "01012345678"
        val shop = Shop.create(seller, shopName, tel)

        //when
        val updatedShopName = "update_shop"
        shop.updateShopName(updatedShopName)

        //then
        Assertions.assertThat(shop.shopName)
            .isEqualTo(updatedShopName)
    }

    @Test
    fun updateTelTest() {
        //given
        val seller = Member.create("test@gmail.com", "1234", "1234567898765", Role.MEMBER, Address("seoul", "잠실-1-1", "102동 102호"))
        val shopName = "test_shop"
        val tel = "01012345678"
        val shop = Shop.create(seller, shopName, tel)

        //when
        val updatedTel = "0212345678"
        shop.updateTel(updatedTel)

        //then
        Assertions.assertThat(shop.tel)
            .isEqualTo(updatedTel)
    }
}
package intelligent_commerce.intelligent_commerce.item.domain

import intelligent_commerce.intelligent_commerce.member.domain.Address
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.domain.Role
import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ItemTest {

    private fun createSellerShopAndItem(title: String, content: String, price: Long, deliveryCharge: Long, remaining: Long): Item {
        val seller = Member.create("test@gmail.com", "1234", "1234567898765", Role.SELLER, Address("seoul", "잠실-1-1", "102동 102호"))
        val shop = Shop.create(seller, "test_shop", "01012345678")
        return Item.create(shop, title, content, price, deliveryCharge, remaining)
    }

    @Test
    fun updateTitleTest() {
        //given
        val title = "test_update_title"
        val content = "update_title"
        val price: Long = 10000
        val deliveryCharge: Long = 1000
        val remaining: Long = 10
        val item = createSellerShopAndItem(title, content, price, deliveryCharge, remaining)

        //when
        val updatedTitle = "updated_title"
        item.updateTitle(updatedTitle)

        //then
        Assertions.assertThat(item.title).isEqualTo(updatedTitle)
    }

    @Test
    fun updateContentTest() {
        //given
        val title = "test_update_content"
        val content = "update_content"
        val price: Long = 20000
        val deliveryCharge: Long = 2000
        val remaining: Long = 20
        val item = createSellerShopAndItem(title, content, price, deliveryCharge, remaining)

        //when
        val updatedContent = "update_content"
        item.updateContent(updatedContent)

        //then
        Assertions.assertThat(item.content).isEqualTo(updatedContent)
    }

    @Test
    fun updatePriceTest() {
        //given
        val title = "test_update_price"
        val content = "update_price"
        val price: Long = 30000
        val deliveryCharge: Long = 3000
        val remaining: Long = 30
        val item = createSellerShopAndItem(title, content, price, deliveryCharge, remaining)

        //when
        val updatedPrice: Long = 35000
        item.updatePrice(updatedPrice)

        //then
        Assertions.assertThat(item.price).isEqualTo(updatedPrice)
    }

    @Test
    fun updateDeliverCharge() {
        //given
        val title = "test_update_delivery_charge"
        val content = "update_delivery_charge"
        val price: Long = 40000
        val deliveryCharge: Long = 4000
        val remaining: Long = 40
        val item = createSellerShopAndItem(title, content, price, deliveryCharge, remaining)

        //when
        val updatedDeliveryCharge: Long = 5000
        item.updateDeliveryCharge(updatedDeliveryCharge)

        //then
        Assertions.assertThat(item.deliveryCharge).isEqualTo(updatedDeliveryCharge)
    }

    @Test
    fun addRemainingTest() {
        //given
        val title = "test_add_remaining"
        val content = "add_remaining"
        val price: Long = 50000
        val deliveryCharge: Long = 5000
        val remaining: Long = 50
        val item = createSellerShopAndItem(title, content, price, deliveryCharge, remaining)

        //when
        val wantAddRemaining: Long = 20
        item.addRemaining(wantAddRemaining)

        //then
        Assertions.assertThat(item.remaining).isEqualTo(remaining + wantAddRemaining)
    }

    @Test
    fun minusRemainingTest() {
        //given
        val title = "test_minus_remaining"
        val content = "minus_remaining"
        val price: Long = 60000
        val deliveryCharge: Long = 6000
        val remaining: Long = 60
        val item = createSellerShopAndItem(title, content, price, deliveryCharge, remaining)

        //when
        item.minusRemaining()

        //then
        Assertions.assertThat(item.remaining).isEqualTo(remaining - 1)
    }

    @Test
    fun rollbackMinusRemainingTest() {
        //given
        val title = "test_rollback_minus_remaining"
        val content = "rollback_minus_remaining"
        val price: Long = 70000
        val deliveryCharge: Long = 7000
        val remaining: Long = 70
        val item = createSellerShopAndItem(title, content, price, deliveryCharge, remaining)
        item.minusRemaining()

        //when
        item.rollbackMinusRemaining()

        //then
        Assertions.assertThat(item.remaining).isEqualTo(remaining)
    }
}
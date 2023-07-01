package intelligent_commerce.intelligent_commerce.item.domain

import intelligent_commerce.intelligent_commerce.member.domain.Address
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.domain.Role
import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ItemTest {

    @Test
    fun updateTitleTest() {
        //given
        val seller = Member.create("test@gmail.com", "1234", "1234567898765", Role.SELLER, Address("seoul", "잠실-1-1", "102동 102호"))
        val shop = Shop.create(seller, "test_shop", "01012345678")
        val title = "test_item"
        val content = "test_content"
        val price: Long = 30000
        val remaining: Long = 10
        val item = Item.create(shop, title, content, price, remaining)

        //when
        val updatedTitle = "updated_title"
        item.updateTitle(updatedTitle)

        //then
        Assertions.assertThat(item.title).isEqualTo(updatedTitle)
    }

    @Test
    fun updateContentTest() {
        //given
        val seller = Member.create("test@gmail.com", "1234", "1234567898765", Role.SELLER, Address("seoul", "잠실-1-1", "102동 102호"))
        val shop = Shop.create(seller, "test_shop", "01012345678")
        val title = "test_item"
        val content = "test_content"
        val price: Long = 30000
        val remaining: Long = 10
        val item = Item.create(shop, title, content, price, remaining)

        //when
        val updatedContent = "update_content"
        item.updateContent(updatedContent)

        //then
        Assertions.assertThat(item.content).isEqualTo(updatedContent)
    }

    @Test
    fun updatePriceTest() {
        //given
        val seller = Member.create("test@gmail.com", "1234", "1234567898765", Role.SELLER, Address("seoul", "잠실-1-1", "102동 102호"))
        val shop = Shop.create(seller, "test_shop", "01012345678")
        val title = "test_item"
        val content = "test_content"
        val price: Long = 30000
        val remaining: Long = 10
        val item = Item.create(shop, title, content, price, remaining)

        //when
        val updatedPrice: Long = 25000
        item.updatePrice(updatedPrice)

        //then
        Assertions.assertThat(item.price).isEqualTo(updatedPrice)
    }

    @Test
    fun addRemainingTest() {
        //given
        val seller = Member.create("test@gmail.com", "1234", "1234567898765", Role.SELLER, Address("seoul", "잠실-1-1", "102동 102호"))
        val shop = Shop.create(seller, "test_shop", "01012345678")
        val title = "test_item"
        val content = "test_content"
        val price: Long = 30000
        val remaining: Long = 10
        val item = Item.create(shop, title, content, price, remaining)

        //when
        val wantAddRemaining: Long = 20
        item.addRemaining(wantAddRemaining)

        //then
        Assertions.assertThat(item.remaining).isEqualTo(remaining + wantAddRemaining)
    }

    @Test
    fun minusRemainingTest() {
        //given
        val seller = Member.create("test@gmail.com", "1234", "1234567898765", Role.SELLER, Address("seoul", "잠실-1-1", "102동 102호"))
        val shop = Shop.create(seller, "test_shop", "01012345678")
        val title = "test_item"
        val content = "test_content"
        val price: Long = 30000
        val remaining: Long = 10
        val item = Item.create(shop, title, content, price, remaining)

        //when
        item.minusRemaining()

        //then
        Assertions.assertThat(item.remaining).isEqualTo(remaining - 1)
    }

    @Test
    fun rollbackMinusRemainingTest() {
        //given
        val seller = Member.create("test@gmail.com", "1234", "1234567898765", Role.SELLER, Address("seoul", "잠실-1-1", "102동 102호"))
        val shop = Shop.create(seller, "test_shop", "01012345678")
        val title = "test_item"
        val content = "test_content"
        val price: Long = 30000
        val remaining: Long = 10
        val item = Item.create(shop, title, content, price, remaining)
        item.minusRemaining()

        //when
        item.rollbackMinusRemaining()

        //then
        Assertions.assertThat(item.remaining).isEqualTo(remaining)
    }
}
package intelligent_commerce.intelligent_commerce.order.domain

import intelligent_commerce.intelligent_commerce.item.domain.Item
import intelligent_commerce.intelligent_commerce.member.domain.Address
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.domain.Role
import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class OrdersTest {

    private fun createSellerShopAndItem(): Item {
        val seller = Member.create("test@gmail.com", "1234", "1234567898765", Role.SELLER, Address("서울", "잠실-1-1", "102동 102호"))
        val shop = Shop.create(seller, "test_shop", "01012345678")
        return Item.create(shop, "test_item", "test_item_content", 15000, 1500, 30)
    }

    @Test
    fun deliveryCompleted() {
        //given
        val buyer = Member.create("order_member_test@gmail.com", "1111", "9876543212345", Role.MEMBER, Address("서울", "강남-1-1", "101동 102호"))
        val order = Orders.create(buyer, createSellerShopAndItem(), 0, 1)

        //when
        order.deliveryCompleted()

        //then
        Assertions.assertThat(order.orderState).isEqualTo(OrderState.DELIVERY_COMPLETED)
    }

    @Test
    fun cancelOrder() {
        //given
        val buyer = Member.create("order_member_test@gmail.com", "1111", "9876543212345", Role.MEMBER, Address("서울", "강남-1-1", "101동 102호"))
        val order = Orders.create(buyer, createSellerShopAndItem(), 0, 1)

        //when
        order.cancelOrder()

        //then
        Assertions.assertThat(order.orderState).isEqualTo(OrderState.CANCEL)
    }
}
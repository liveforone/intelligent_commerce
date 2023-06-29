package intelligent_commerce.intelligent_commerce.mileage.domain

import intelligent_commerce.intelligent_commerce.member.domain.Address
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.domain.Role
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MileageTest {

    @Test
    fun addPointTest() {
        //given
        val member = Member.create("test@gmail.com", "1234", "1234567898765", Role.MEMBER, Address("seoul", "잠실-1-1", "102동 102호"))
        val mileage = Mileage.create(member)

        //when
        val itemPrice: Long = 10000
        mileage.addPoint(itemPrice)

        //then
        Assertions.assertThat(mileage.mileagePoint)
            .isEqualTo(MileagePolicy.calculateMileage(itemPrice))
    }

    @Test
    fun rollbackPointTest() {
        //given
        val member = Member.create("test@gmail.com", "1234", "1234567898765", Role.MEMBER, Address("seoul", "잠실-1-1", "102동 102호"))
        val mileage = Mileage.create(member)
        val itemPrice: Long = 20000
        mileage.addPoint(itemPrice)

        //when
        mileage.rollbackAddPoint(itemPrice)

        //then
        Assertions.assertThat(mileage.mileagePoint)
            .isEqualTo(0)
    }

    @Test
    fun subtractPointTest() {
        //given
        val member = Member.create("test@gmail.com", "1234", "1234567898765", Role.MEMBER, Address("seoul", "잠실-1-1", "102동 102호"))
        val mileage = Mileage.create(member)
        val itemPrice: Long = 30000
        mileage.addPoint(itemPrice)

        //when
        val pointToUse: Long = 200
        mileage.subtractPoint(pointToUse)

        //then
        Assertions.assertThat(mileage.mileagePoint)
            .isEqualTo(MileagePolicy.calculateMileage(itemPrice) - pointToUse)
    }

    @Test
    fun rollbackSubtractPointTest() {
        //given
        val member = Member.create("test@gmail.com", "1234", "1234567898765", Role.MEMBER, Address("seoul", "잠실-1-1", "102동 102호"))
        val mileage = Mileage.create(member)
        val itemPrice: Long = 30000
        mileage.addPoint(itemPrice)
        val pointToUse: Long = 200
        mileage.subtractPoint(pointToUse)

        //when
        mileage.rollbackSubtractPoint(pointToUse)

        //then
        Assertions.assertThat(mileage.mileagePoint)
            .isEqualTo(MileagePolicy.calculateMileage(itemPrice))
    }
}
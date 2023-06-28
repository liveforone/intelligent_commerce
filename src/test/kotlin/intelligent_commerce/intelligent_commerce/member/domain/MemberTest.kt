package intelligent_commerce.intelligent_commerce.member.domain

import intelligent_commerce.intelligent_commerce.member.domain.util.PasswordUtil
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MemberTest {

    @Test
    fun updatePwTest() {
        //given
        val email = "test_updatePw@gmail.com"
        val pw = "1234"
        val bankbookNum = "1234567898765"
        val member = Member.create(email, pw, bankbookNum, Role.MEMBER, Address("seoul", "잠실-1-1", "102동 102호"))

        //when
        val newPassword = "1111"
        member.updatePw(newPassword, pw)

        //then
        Assertions.assertThat(PasswordUtil.isMatchPassword(newPassword, member.pw))
            .isTrue()
    }

    @Test
    fun updateBankbookNumTest() {
        //given
        val email = "test_updateBankbook@gmail.com"
        val pw = "1234"
        val bankbookNum = "1234567898765"
        val member = Member.create(email, pw, bankbookNum, Role.MEMBER, Address("seoul", "잠실-1-1", "102동 102호"))

        //when
        val newBankbookNum = "9876543212345"
        member.updateBankbookNum(newBankbookNum)

        //then
        Assertions.assertThat(member.bankbookNum)
            .isEqualTo(newBankbookNum)
    }

    @Test
    fun updateAddressTest() {
        //given
        val email = "test_updateBankbook@gmail.com"
        val pw = "1234"
        val bankbookNum = "1234567898765"
        val member = Member.create(email, pw, bankbookNum, Role.MEMBER, Address("seoul", "잠실-1-1", "102동 102호"))

        //when
        val newCity = "seoul"
        val newRoadNum = "종로-1-1"
        val newDetail = "301동 505호"
        member.updateAddress(newCity, newRoadNum, newDetail)

        //then
        Assertions.assertThat(member.address.city)
            .isEqualTo(newCity)
        Assertions.assertThat(member.address.roadNum)
            .isEqualTo(newRoadNum)
        Assertions.assertThat(member.address.detail)
            .isEqualTo(newDetail)
    }
}
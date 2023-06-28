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
        val member = Member.create(email, pw, bankbookNum, Role.MEMBER)

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
        val member = Member.create(email, pw, bankbookNum, Role.MEMBER)

        //when
        val newBankbookNum = "9876543212345"
        member.updateBankbookNum(newBankbookNum)

        //then
        Assertions.assertThat(member.bankbookNum)
            .isEqualTo(newBankbookNum)
    }
}
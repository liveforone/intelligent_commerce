package intelligent_commerce.intelligent_commerce.member.service.command

import intelligent_commerce.intelligent_commerce.member.domain.Address
import intelligent_commerce.intelligent_commerce.member.domain.Role
import intelligent_commerce.intelligent_commerce.member.dto.request.SignupRequest
import intelligent_commerce.intelligent_commerce.member.dto.update.UpdateAddress
import intelligent_commerce.intelligent_commerce.member.dto.update.UpdateBankbookNum
import intelligent_commerce.intelligent_commerce.member.service.query.MemberQueryService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class MemberCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val memberQueryService: MemberQueryService
) {

    @Test
    @Transactional
    fun createMemberTest() {
        //given
        val email = "member_test@gmail.com"
        val pw = "1234"
        val bankbookNum = "1234567898765"
        val city = "seoul"
        val roadNum = "잠실-1-1"
        val detail = "102동 102호"

        //when
        val signupRequest = SignupRequest(email, pw, bankbookNum, city, roadNum, detail)
        val identity = memberCommandService.createMember(signupRequest)
        entityManager.flush()
        entityManager.clear()

        //then
        Assertions.assertThat(memberQueryService.getMemberByIdentity(identity).auth)
            .isEqualTo(Role.MEMBER)
    }

    @Test
    @Transactional
    fun createSellerTest() {
        //given
        val email = "seller_test@gmail.com"
        val pw = "1234"
        val bankbookNum = "1234567898765"
        val city = "seoul"
        val roadNum = "잠실-1-1"
        val detail = "102동 102호"

        //when
        val signupRequest = SignupRequest(email, pw, bankbookNum, city, roadNum, detail)
        val identity = memberCommandService.createSeller(signupRequest)
        entityManager.flush()
        entityManager.clear()

        //then
        Assertions.assertThat(memberQueryService.getMemberByIdentity(identity).auth)
            .isEqualTo(Role.SELLER)
    }

    @Test
    @Transactional
    fun updateBankbookNumTest() {
        //given
        val email = "update_bankbookNum_test@gmail.com"
        val pw = "1234"
        val bankbookNum = "1234567898765"
        val city = "seoul"
        val roadNum = "잠실-1-1"
        val detail = "102동 102호"
        val signupRequest = SignupRequest(email, pw, bankbookNum, city, roadNum, detail)
        val identity = memberCommandService.createSeller(signupRequest)
        entityManager.flush()
        entityManager.clear()

        //when
        val newBankbookNum = "9876543212345"
        val request = UpdateBankbookNum(newBankbookNum)
        memberCommandService.updateBankbookNum(request, identity)
        entityManager.flush()
        entityManager.clear()

        //then
        Assertions.assertThat(memberQueryService.getMemberByIdentity(identity).bankbookNum)
            .isEqualTo(newBankbookNum)
    }

    @Test
    @Transactional
    fun updateAddressTest() {
        //given
        val email = "update_bankbookNum_test@gmail.com"
        val pw = "1234"
        val bankbookNum = "1234567898765"
        val city = "seoul"
        val roadNum = "잠실-1-1"
        val detail = "102동 102호"
        val signupRequest = SignupRequest(email, pw, bankbookNum, city, roadNum, detail)
        val identity = memberCommandService.createSeller(signupRequest)
        entityManager.flush()
        entityManager.clear()

        //when
        val newCity = "seoul"
        val newRoadNum = "종로-1-1"
        val newDetail = "301동 505호"
        val request = UpdateAddress(newCity, newRoadNum, newDetail)
        memberCommandService.updateAddress(request, identity)
        entityManager.flush()
        entityManager.clear()

        //then
        Assertions.assertThat(memberQueryService.getMemberByIdentity(identity).address)
            .isEqualTo(Address(newCity, newRoadNum, newDetail))
    }
}
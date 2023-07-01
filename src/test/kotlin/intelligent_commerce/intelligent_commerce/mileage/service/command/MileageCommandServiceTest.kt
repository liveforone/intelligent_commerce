package intelligent_commerce.intelligent_commerce.mileage.service.command

import intelligent_commerce.intelligent_commerce.member.dto.request.SignupRequest
import intelligent_commerce.intelligent_commerce.member.service.command.MemberCommandService
import intelligent_commerce.intelligent_commerce.mileage.domain.MileagePolicy
import intelligent_commerce.intelligent_commerce.mileage.service.query.MileageQueryService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class MileageCommandServiceTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberCommandService: MemberCommandService,
    private val mileageCommandService: MileageCommandService,
    private val mileageQueryService: MileageQueryService
) {

    fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    @Transactional
    fun createMileageTest() {
        //given
        val signupRequest = SignupRequest("mileage_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createMember(signupRequest)
        flushAndClear()

        //when
        mileageCommandService.createMileage(identity)
        flushAndClear()

        //then
        Assertions.assertThat(mileageQueryService.getMileageByMemberIdentity(identity))
            .isNotNull
    }

    @Test
    @Transactional
    fun addPointTest() {
        //given
        val signupRequest = SignupRequest("mileage_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createMember(signupRequest)
        flushAndClear()
        mileageCommandService.createMileage(identity)
        flushAndClear()

        //when
        val itemPrice: Long = 50000
        mileageCommandService.addPoint(itemPrice, identity)
        flushAndClear()

        //then
        Assertions.assertThat(mileageQueryService.getMileageByMemberIdentity(identity).mileagePoint)
            .isEqualTo(MileagePolicy.calculateMileage(itemPrice))
    }

    @Test
    @Transactional
    fun rollbackPointTest() {
        //given
        val signupRequest = SignupRequest("mileage_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createMember(signupRequest)
        flushAndClear()
        mileageCommandService.createMileage(identity)
        flushAndClear()
        val itemPrice: Long = 50000
        mileageCommandService.addPoint(itemPrice, identity)
        flushAndClear()

        //when
        mileageCommandService.rollbackAddPoint(itemPrice, identity)
        flushAndClear()

        //then
        Assertions.assertThat(mileageQueryService.getMileageByMemberIdentity(identity).mileagePoint)
            .isEqualTo(0)
    }

    @Test
    @Transactional
    fun subtractPointTest() {
        //given
        val signupRequest = SignupRequest("mileage_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createMember(signupRequest)
        flushAndClear()
        mileageCommandService.createMileage(identity)
        flushAndClear()
        val itemPrice: Long = 500000
        mileageCommandService.addPoint(itemPrice, identity)
        flushAndClear()

        //when
        val pointToUse: Long = 4000
        mileageCommandService.subtractPoint(pointToUse, identity)
        flushAndClear()

        //then
        Assertions.assertThat(mileageQueryService.getMileageByMemberIdentity(identity).mileagePoint)
            .isEqualTo(MileagePolicy.calculateMileage(itemPrice) - pointToUse)
    }

    @Test
    @Transactional
    fun rollbackSubtractPointTest() {
        //given
        val signupRequest = SignupRequest("mileage_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createMember(signupRequest)
        flushAndClear()
        mileageCommandService.createMileage(identity)
        flushAndClear()
        val itemPrice: Long = 500000
        mileageCommandService.addPoint(itemPrice, identity)
        flushAndClear()
        val pointToUse: Long = 400
        mileageCommandService.subtractPoint(pointToUse, identity)
        flushAndClear()

        //when
        mileageCommandService.rollbackSubtractPoint(pointToUse, identity)
        flushAndClear()

        //then
        Assertions.assertThat(mileageQueryService.getMileageByMemberIdentity(identity).mileagePoint)
            .isEqualTo(MileagePolicy.calculateMileage(itemPrice))
    }
}
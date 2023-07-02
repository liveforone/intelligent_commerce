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

    /*
    * 마일리지는 회원가입시 자동으로 insert 된다.
     */
    @Test
    @Transactional
    fun createMileageTest() {
        //given
        val signupRequest = SignupRequest("mileage_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")

        //when
        val identity = memberCommandService.createMember(signupRequest)
        flushAndClear()


        //then
        Assertions.assertThat(mileageQueryService.getMileageByIdentity(identity))
            .isNotNull
    }

    @Test
    @Transactional
    fun addPointTest() {
        //given
        val signupRequest = SignupRequest("mileage_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createMember(signupRequest)
        flushAndClear()

        //when
        val itemPrice: Long = 50000
        mileageCommandService.addPoint(itemPrice, identity)
        flushAndClear()

        //then
        Assertions.assertThat(mileageQueryService.getMileageByIdentity(identity).mileagePoint)
            .isEqualTo(MileagePolicy.calculateMileage(itemPrice))
    }

    @Test
    @Transactional
    fun subtractPointTest() {
        //given
        val signupRequest = SignupRequest("mileage_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createMember(signupRequest)
        flushAndClear()
        val itemPrice: Long = 500000
        mileageCommandService.addPoint(itemPrice, identity)
        flushAndClear()

        //when
        val pointToUse: Long = 4000
        mileageCommandService.subtractPoint(pointToUse, identity)
        flushAndClear()

        //then
        Assertions.assertThat(mileageQueryService.getMileageByIdentity(identity).mileagePoint)
            .isEqualTo(MileagePolicy.calculateMileage(itemPrice) - pointToUse)
    }

    @Test
    @Transactional
    fun rollbackMileageTest() {
        //given
        val signupRequest = SignupRequest("mileage_test@gmail.com", "1234", "1234567898765", "서울", "잠실-1-1", "102동 102호")
        val identity = memberCommandService.createMember(signupRequest)
        flushAndClear()
        val itemPrice: Long = 500000
        mileageCommandService.addPoint(itemPrice, identity)
        flushAndClear()
        val pointToUse: Long = 4000
        mileageCommandService.subtractPoint(pointToUse, identity)
        flushAndClear()

        //when
        mileageCommandService.rollbackMileage(pointToUse, itemPrice, identity)
        flushAndClear()

        //then
        Assertions.assertThat(mileageQueryService.getMileageByIdentity(identity).mileagePoint)
            .isEqualTo(0)
    }
}
package intelligent_commerce.intelligent_commerce.member.service.command

import intelligent_commerce.intelligent_commerce.exception.exception.MemberException
import intelligent_commerce.intelligent_commerce.exception.message.MemberExceptionMessage
import intelligent_commerce.intelligent_commerce.jwt.JwtTokenProvider
import intelligent_commerce.intelligent_commerce.jwt.TokenInfo
import intelligent_commerce.intelligent_commerce.member.domain.Address
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.dto.request.LoginRequest
import intelligent_commerce.intelligent_commerce.member.dto.request.SignupRequest
import intelligent_commerce.intelligent_commerce.member.dto.update.UpdateBankbookNum
import intelligent_commerce.intelligent_commerce.member.dto.update.UpdatePassword
import intelligent_commerce.intelligent_commerce.member.repository.MemberRepository
import intelligent_commerce.intelligent_commerce.member.domain.Role
import intelligent_commerce.intelligent_commerce.member.domain.util.PasswordUtil
import intelligent_commerce.intelligent_commerce.member.dto.request.WithdrawRequest
import intelligent_commerce.intelligent_commerce.member.dto.update.UpdateAddress
import intelligent_commerce.intelligent_commerce.mileage.service.command.MileageCommandService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberCommandService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val mileageCommandService: MileageCommandService
) {

    fun createMember(signupRequest: SignupRequest): String {
        return Member.create(
            signupRequest.email!!,
            signupRequest.pw!!,
            signupRequest.bankbookNum!!,
            Role.MEMBER,
            Address(signupRequest.city!!, signupRequest.roadNum!!, signupRequest.detail!!)
        ).also {
            memberRepository.save(it)
            mileageCommandService.createMileage(it.identity)
        }.identity
    }

    fun createSeller(signupRequest: SignupRequest): String {
        return Member.create(
            signupRequest.email!!,
            signupRequest.pw!!,
            signupRequest.bankbookNum!!,
            Role.SELLER,
            Address(signupRequest.city!!, signupRequest.roadNum!!, signupRequest.detail!!)
        ).also {
                memberRepository.save(it)
        }.identity
    }

    fun login(loginRequest: LoginRequest): TokenInfo {
        val authentication: Authentication = authenticationManagerBuilder
            .`object`
            .authenticate(UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.pw))

        return jwtTokenProvider.generateToken(authentication)
    }

    fun updatePassword(updatePassword: UpdatePassword, identity: String) {
        memberRepository.findOneByIdentity(identity)
            .also {
                it.updatePw(updatePassword.password!!, updatePassword.oldPassword!!)
            }
    }

    fun updateBankbookNum(updateBankbookNum: UpdateBankbookNum, identity: String) {
        memberRepository.findOneByIdentity(identity)
            .also {
                it.updateBankbookNum(updateBankbookNum.bankbookNum!!)
            }
    }

    fun updateAddress(updateAddress: UpdateAddress, identity: String) {
        memberRepository.findOneByIdentity(identity)
            .also {
                it.updateAddress(updateAddress.city!!, updateAddress.roadNum!!, updateAddress.detail!!)
            }
    }

    fun withdraw(withdrawRequest: WithdrawRequest, identity: String) {
        val member = memberRepository.findOneByIdentity(identity)
            .also {
                if (!PasswordUtil.isMatchPassword(withdrawRequest.pw!!, it.pw))
                    throw MemberException(MemberExceptionMessage.WRONG_PASSWORD)
            }
        memberRepository.delete(member)
    }
}
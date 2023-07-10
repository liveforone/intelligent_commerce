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
import intelligent_commerce.intelligent_commerce.member.dto.update.UpdateEmail
import intelligent_commerce.intelligent_commerce.member.service.validator.MemberServiceValidator
import intelligent_commerce.intelligent_commerce.mileage.service.command.MileageCommandService
import intelligent_commerce.intelligent_commerce.shop.service.command.ShopCommandService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberCommandService @Autowired constructor(
    private val memberServiceValidator: MemberServiceValidator,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository,
    private val shopCommandService: ShopCommandService,
    private val mileageCommandService: MileageCommandService,
) {

    fun createMember(signupRequest: SignupRequest): String {
        return with(signupRequest) {
            memberServiceValidator.validateDuplicateEmail(email!!)
            Member.create(
                email!!,
                pw!!,
                bankbookNum!!,
                auth = Role.MEMBER,
                Address(city!!, roadNum!!, detail!!)
            ).run {
                memberRepository.save(this)
                mileageCommandService.createMileage(identity)
                identity
            }
        }
    }

    fun createSeller(signupRequest: SignupRequest): String {
        return with(signupRequest) {
            Member.create(
                email!!,
                pw!!,
                bankbookNum!!,
                auth = Role.SELLER,
                Address(city!!, roadNum!!, detail!!)
            ).run { memberRepository.save(this).identity }
        }
    }

    fun login(loginRequest: LoginRequest): TokenInfo {
        val authentication: Authentication = authenticationManagerBuilder
            .`object`
            .authenticate(UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.pw))

        return jwtTokenProvider.generateToken(authentication)
    }

    fun updateEmail(updateEmail: UpdateEmail, identity: String) {
        with(updateEmail) {
            memberServiceValidator.validateDuplicateEmail(newEmail!!)
            memberRepository.findOneByIdentity(identity)
                .also { it.updateEmail(newEmail!!) }
        }
    }

    fun updatePassword(updatePassword: UpdatePassword, identity: String) {
        with(updatePassword) {
            memberRepository.findOneByIdentity(identity)
                .also { it.updatePw(password!!, oldPassword!!) }
        }
    }

    fun updateBankbookNum(updateBankbookNum: UpdateBankbookNum, identity: String) {
        memberRepository.findOneByIdentity(identity)
            .also { it.updateBankbookNum(updateBankbookNum.bankbookNum!!) }
    }

    fun updateAddress(updateAddress: UpdateAddress, identity: String) {
        with(updateAddress) {
            memberRepository.findOneByIdentity(identity)
                .also { it.updateAddress(city!!, roadNum!!, detail!!) }
        }
    }

    fun withdraw(withdrawRequest: WithdrawRequest, identity: String) {
        memberRepository.findOneByIdentity(identity)
            .takeIf { PasswordUtil.isMatchPassword(withdrawRequest.pw!!, it.pw) }
            ?.also {
                if (it.isSeller()) shopCommandService.deleteShop(identity)
                else mileageCommandService.deleteMileage(identity)
                memberRepository.delete(it)
            }
            ?: throw MemberException(MemberExceptionMessage.WRONG_PASSWORD)
    }
}
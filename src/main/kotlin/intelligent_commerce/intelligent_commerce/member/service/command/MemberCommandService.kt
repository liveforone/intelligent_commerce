package intelligent_commerce.intelligent_commerce.member.service.command

import intelligent_commerce.intelligent_commerce.jwt.JwtTokenProvider
import intelligent_commerce.intelligent_commerce.jwt.TokenInfo
import intelligent_commerce.intelligent_commerce.member.domain.Member
import intelligent_commerce.intelligent_commerce.member.dto.request.LoginRequest
import intelligent_commerce.intelligent_commerce.member.dto.request.SignupRequest
import intelligent_commerce.intelligent_commerce.member.dto.update.UpdateBankbookNum
import intelligent_commerce.intelligent_commerce.member.dto.update.UpdatePassword
import intelligent_commerce.intelligent_commerce.member.repository.MemberRepository
import intelligent_commerce.intelligent_commerce.member.domain.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberCommandService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun createMember(signupRequest: SignupRequest): String {
        return Member.create(signupRequest.email!!, signupRequest.pw!!, signupRequest.bankbookNum!!, Role.MEMBER)
            .also {
                memberRepository.save(it)
            }.identity
    }

    fun createSeller(signupRequest: SignupRequest): String {
        return Member.create(signupRequest.email!!, signupRequest.pw!!, signupRequest.bankbookNum!!, Role.SELLER)
            .also {
                memberRepository.save(it)
            }.identity
    }

    fun login(loginRequest: LoginRequest): TokenInfo {
        val member = memberRepository.findOneByEmail(loginRequest.email!!)

        authenticationManagerBuilder.also {
            it.`object`.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.pw)
            )
        }

        return jwtTokenProvider.generateToken(member)
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
}
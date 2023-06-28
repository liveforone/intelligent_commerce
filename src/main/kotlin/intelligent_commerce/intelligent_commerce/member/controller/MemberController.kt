package intelligent_commerce.intelligent_commerce.member.controller

import intelligent_commerce.intelligent_commerce.member.controller.constant.MemberControllerLog
import intelligent_commerce.intelligent_commerce.member.controller.constant.MemberUrl
import intelligent_commerce.intelligent_commerce.member.controller.response.MemberResponse
import intelligent_commerce.intelligent_commerce.member.dto.request.LoginRequest
import intelligent_commerce.intelligent_commerce.validator.ControllerValidator
import intelligent_commerce.intelligent_commerce.member.dto.request.SignupRequest
import intelligent_commerce.intelligent_commerce.member.dto.update.UpdateBankbookNum
import intelligent_commerce.intelligent_commerce.member.dto.update.UpdatePassword
import intelligent_commerce.intelligent_commerce.member.service.command.MemberCommandService
import intelligent_commerce.intelligent_commerce.member.service.query.MemberQueryService
import intelligent_commerce.intelligent_commerce.jwt.constant.JwtConstant
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!

@RestController
class MemberController @Autowired constructor(
    private val memberCommandService: MemberCommandService,
    private val memberQueryService: MemberQueryService,
    private val controllerValidator: ControllerValidator
) {

    @PostMapping(MemberUrl.SIGNUP_MEMBER)
    fun signupMember(
        @RequestBody @Valid signupRequest: SignupRequest,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        memberCommandService.createMember(signupRequest)
        logger().info(MemberControllerLog.SIGNUP_SUCCESS.log)

        return MemberResponse.signupSuccess()
    }

    @PostMapping(MemberUrl.SIGNUP_SELLER)
    fun signupSeller(
        @RequestBody @Valid signupRequest: SignupRequest,
        bindingResult: BindingResult
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        memberCommandService.createSeller(signupRequest)
        logger().info(MemberControllerLog.SIGNUP_SUCCESS.log)

        return MemberResponse.signupSuccess()
    }

    @PostMapping(MemberUrl.LOGIN)
    fun login(
        @RequestBody @Valid loginRequest: LoginRequest,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        val tokenInfo = memberCommandService.login(loginRequest)
        response.addHeader(JwtConstant.ACCESS_TOKEN, tokenInfo.accessToken)
        response.addHeader(JwtConstant.REFRESH_TOKEN, tokenInfo.refreshToken)
        logger().info(MemberControllerLog.LOGIN_SUCCESS.log)

        return MemberResponse.loginSuccess()
    }

    @GetMapping(MemberUrl.INFO)
    fun info(principal: Principal): ResponseEntity<*> {
        val member = memberQueryService.getMemberByIdentity(identity = principal.name)
        return MemberResponse.infoSuccess(member)
    }

    @PutMapping(MemberUrl.UPDATE_PASSWORD)
    fun updatePassword(
        @RequestBody @Valid updatePassword: UpdatePassword,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        memberCommandService.updatePassword(updatePassword, identity = principal.name)
        logger().info(MemberControllerLog.UPDATE_PW_SUCCESS.log)

        return MemberResponse.updatePwSuccess()
    }

    @PutMapping(MemberUrl.UPDATE_BANKBOOK_NUM)
    fun updateBankbookNum(
        @RequestBody @Valid updateBankbookNum: UpdateBankbookNum,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        memberCommandService.updateBankbookNum(updateBankbookNum, identity = principal.name)
        logger().info(MemberControllerLog.UPDATE_BANKBOOK_NUM_SUCCESS.log)

        return MemberResponse.updateBankbookNumSuccess()
    }
}
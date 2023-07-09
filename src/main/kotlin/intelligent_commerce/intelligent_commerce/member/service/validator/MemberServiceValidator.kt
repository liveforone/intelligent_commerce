package intelligent_commerce.intelligent_commerce.member.service.validator

import intelligent_commerce.intelligent_commerce.exception.exception.MemberException
import intelligent_commerce.intelligent_commerce.exception.message.MemberExceptionMessage
import intelligent_commerce.intelligent_commerce.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MemberServiceValidator @Autowired constructor(
    private val memberRepository: MemberRepository
) {

    fun validateDuplicateEmail(email: String) {
        memberRepository.findIdByEmailNullableForValidate(email)
            ?: throw MemberException(MemberExceptionMessage.DUPLICATE_EMAIL)
    }
}
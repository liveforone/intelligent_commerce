package intelligent_commerce.intelligent_commerce.exception.exception

import intelligent_commerce.intelligent_commerce.exception.message.MemberExceptionMessage
import java.lang.RuntimeException

class MemberException(val memberExceptionMessage: MemberExceptionMessage) : RuntimeException(memberExceptionMessage.message) {
}
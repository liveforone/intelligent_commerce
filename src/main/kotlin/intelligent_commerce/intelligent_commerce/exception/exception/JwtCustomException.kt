package intelligent_commerce.intelligent_commerce.exception.exception

import intelligent_commerce.intelligent_commerce.exception.message.JwtExceptionMessage
import java.lang.RuntimeException

class JwtCustomException(val jwtExceptionMessage: JwtExceptionMessage) : RuntimeException(jwtExceptionMessage.message) {
}
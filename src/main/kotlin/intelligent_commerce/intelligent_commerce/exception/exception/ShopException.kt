package intelligent_commerce.intelligent_commerce.exception.exception

import intelligent_commerce.intelligent_commerce.exception.message.ShopExceptionMessage
import java.lang.RuntimeException

class ShopException(val shopExceptionMessage: ShopExceptionMessage) : RuntimeException(shopExceptionMessage.message) {
}
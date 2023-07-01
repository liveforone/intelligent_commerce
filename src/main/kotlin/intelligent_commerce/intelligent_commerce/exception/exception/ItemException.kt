package intelligent_commerce.intelligent_commerce.exception.exception

import intelligent_commerce.intelligent_commerce.exception.message.ItemExceptionMessage
import java.lang.RuntimeException

class ItemException(val itemExceptionMessage: ItemExceptionMessage) : RuntimeException(itemExceptionMessage.message) {
}
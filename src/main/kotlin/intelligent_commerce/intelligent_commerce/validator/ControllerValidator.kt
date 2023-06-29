package intelligent_commerce.intelligent_commerce.validator

import intelligent_commerce.intelligent_commerce.exception.exception.BindingException
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import java.util.*

@Component
class ControllerValidator {

    fun validateBinding(bindingResult: BindingResult) {
        if (bindingResult.hasErrors()) {
            val errorMessage = Objects.requireNonNull(bindingResult.fieldError)?.defaultMessage
            throw errorMessage?.let { BindingException(it) }!!
        }
    }
}
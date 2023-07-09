package intelligent_commerce.intelligent_commerce.converter

import intelligent_commerce.intelligent_commerce.order.domain.OrderState
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class OrderStateConverter : AttributeConverter<OrderState, String> {
    override fun convertToDatabaseColumn(attribute: OrderState) = attribute.name

    override fun convertToEntityAttribute(dbData: String) = OrderState.valueOf(dbData)
}
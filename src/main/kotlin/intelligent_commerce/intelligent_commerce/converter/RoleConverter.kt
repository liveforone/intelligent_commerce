package intelligent_commerce.intelligent_commerce.converter

import intelligent_commerce.intelligent_commerce.member.domain.Role
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class RoleConverter : AttributeConverter<Role, String> {

    override fun convertToDatabaseColumn(attribute: Role) = attribute.name

    override fun convertToEntityAttribute(dbData: String) = Role.valueOf(dbData)
}
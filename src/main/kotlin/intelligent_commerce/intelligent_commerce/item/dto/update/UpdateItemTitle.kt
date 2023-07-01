package intelligent_commerce.intelligent_commerce.item.dto.update

import jakarta.validation.constraints.NotBlank

data class UpdateItemTitle(
    @field:NotBlank(message = "변경할 상품명을 입력하세요.")
    var title: String?
)
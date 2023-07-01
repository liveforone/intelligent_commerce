package intelligent_commerce.intelligent_commerce.item.dto.update

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UpdateItemTitle(
    @field:NotNull(message = "상품 id를 입력하세요.")
    var id: Long?,
    @field:NotBlank(message = "변경할 상품명을 입력하세요.")
    var title: String?
)

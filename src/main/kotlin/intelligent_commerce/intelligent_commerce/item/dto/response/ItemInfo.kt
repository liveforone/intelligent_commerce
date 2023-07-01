package intelligent_commerce.intelligent_commerce.item.dto.response

data class ItemInfo(
    val id: Long?,
    val shopId: Long,
    val title: String,
    val content: String,
    val price: Long,
    val remaining: Long
)

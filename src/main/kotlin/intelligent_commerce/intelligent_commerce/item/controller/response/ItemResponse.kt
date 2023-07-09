package intelligent_commerce.intelligent_commerce.item.controller.response

import intelligent_commerce.intelligent_commerce.item.dto.response.ItemInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ItemResponse {

    private const val CREATE_ITEM_SUCCESS = "상품을 성공적으로 등록하였습니다."
    private const val UPDATE_TITLE_SUCCESS = "상품명을 성공적으로 변경하였습니다."
    private const val UPDATE_CONTENT_SUCCESS = "상품 설명을 성공적으로 변경하였습니다."
    private const val UPDATE_PRICE_SUCCESS = "상품 가격을 성공적으로 변경하였습니다."
    private const val UPDATE_DELIVERY_CHARGE_SUCCESS = "배송료를 성공적으로 변경하였습니다."
    private const val ADD_REMAINING_SUCCESS = "상품 재고를 성공적으로 추가하였습니다."
    private const val DELETE_ITEM_SUCCESS = "상품을 성공적으로 삭제하였습니다."

    fun itemDetailSuccess(itemInfo: ItemInfo) = ResponseEntity.ok(itemInfo)

    fun itemHomeSuccess(items: List<ItemInfo>) = ResponseEntity.ok(items)

    fun shopItemsSuccess(items: List<ItemInfo>) = ResponseEntity.ok(items)

    fun searchItemSuccess(items: List<ItemInfo>) = ResponseEntity.ok(items)

    fun createItemSuccess(): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(CREATE_ITEM_SUCCESS)
    }

    fun updateTitleSuccess() = ResponseEntity.ok(UPDATE_TITLE_SUCCESS)

    fun updateContentSuccess() = ResponseEntity.ok(UPDATE_CONTENT_SUCCESS)

    fun updatePriceSuccess() = ResponseEntity.ok(UPDATE_PRICE_SUCCESS)

    fun updateDeliveryChargeSuccess() = ResponseEntity.ok(UPDATE_DELIVERY_CHARGE_SUCCESS)

    fun addRemainingSuccess() = ResponseEntity.ok(ADD_REMAINING_SUCCESS)

    fun deleteItemSuccess() = ResponseEntity.ok(DELETE_ITEM_SUCCESS)
}
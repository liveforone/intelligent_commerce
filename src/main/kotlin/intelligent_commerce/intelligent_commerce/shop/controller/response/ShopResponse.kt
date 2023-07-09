package intelligent_commerce.intelligent_commerce.shop.controller.response

import intelligent_commerce.intelligent_commerce.shop.dto.response.ShopInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ShopResponse {

    private const val CREATE_SHOP_SUCCESS = "상점을 성공적으로 생성하였습니다."
    private const val UPDATE_SHOP_NAME_SUCCESS = "상호명을 성공적으로 변경하였습니다."
    private const val UPDATE_TEL = "전화번호를 성공적으로 변경하였습니다."

    fun shopDetailSuccess(shopInfo: ShopInfo) = ResponseEntity.ok(shopInfo)

    fun shopInfoSuccess(shopInfo: ShopInfo) = ResponseEntity.ok(shopInfo)

    fun createShopSuccess(): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(CREATE_SHOP_SUCCESS)
    }

    fun updateShopNameSuccess() = ResponseEntity.ok(UPDATE_SHOP_NAME_SUCCESS)

    fun updateTelSuccess() = ResponseEntity.ok(UPDATE_TEL)
}
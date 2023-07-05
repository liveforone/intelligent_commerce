package intelligent_commerce.intelligent_commerce.shop.controller

import intelligent_commerce.intelligent_commerce.logger
import intelligent_commerce.intelligent_commerce.shop.controller.constant.ShopControllerLog
import intelligent_commerce.intelligent_commerce.shop.controller.constant.ShopParam
import intelligent_commerce.intelligent_commerce.shop.controller.constant.ShopUrl
import intelligent_commerce.intelligent_commerce.shop.controller.response.ShopResponse
import intelligent_commerce.intelligent_commerce.shop.dto.request.CreateShop
import intelligent_commerce.intelligent_commerce.shop.dto.update.UpdateShopName
import intelligent_commerce.intelligent_commerce.shop.dto.update.UpdateTel
import intelligent_commerce.intelligent_commerce.shop.service.command.ShopCommandService
import intelligent_commerce.intelligent_commerce.shop.service.query.ShopQueryService
import intelligent_commerce.intelligent_commerce.validator.ControllerValidator
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class ShopController @Autowired constructor(
    private val shopCommandService: ShopCommandService,
    private val shopQueryService: ShopQueryService,
    private val controllerValidator: ControllerValidator
) {

    @GetMapping(ShopUrl.SHOP_DETAIL)
    fun shopDetail(@PathVariable(ShopParam.ID) id: Long): ResponseEntity<*> {
        val shopDetail = shopQueryService.getShopById(id)
        return ShopResponse.shopDetailSuccess(shopDetail)
    }

    @GetMapping(ShopUrl.SHOP_INFO)
    fun shopInfo(principal: Principal): ResponseEntity<*> {
        val shopInfo = shopQueryService.getShopByIdentity(identity = principal.name)
        return ShopResponse.shopInfoSuccess(shopInfo)
    }

    @PostMapping(ShopUrl.CREATE_SHOP)
    fun createShop(
        @RequestBody @Valid createShop: CreateShop,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        shopCommandService.createShop(createShop, identity = principal.name)
        logger().info(ShopControllerLog.CREATE_SHOP_SUCCESS.log)

        return ShopResponse.createShopSuccess()
    }

    @PutMapping(ShopUrl.UPDATE_SHOP_NAME)
    fun updateShopName(
        @RequestBody @Valid updateShopName: UpdateShopName,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        shopCommandService.updateShopName(updateShopName, identity = principal.name)
        logger().info(ShopControllerLog.UPDATE_SHOP_NAME_SUCCESS.log)

        return ShopResponse.updateShopNameSuccess()
    }

    @PutMapping(ShopUrl.UPDATE_SHOP_TEL)
    fun updateTel(
        @RequestBody @Valid updateTel: UpdateTel,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        shopCommandService.updateTel(updateTel, identity = principal.name)
        logger().info(ShopControllerLog.UPDATE_TEL_SUCCESS.log)

        return ShopResponse.updateTelSuccess()
    }
}
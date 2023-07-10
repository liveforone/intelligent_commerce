package intelligent_commerce.intelligent_commerce.item.controller

import intelligent_commerce.intelligent_commerce.item.controller.constant.ItemControllerLog
import intelligent_commerce.intelligent_commerce.item.controller.constant.ItemParam
import intelligent_commerce.intelligent_commerce.item.controller.constant.ItemUrl
import intelligent_commerce.intelligent_commerce.item.controller.response.ItemResponse
import intelligent_commerce.intelligent_commerce.item.dto.request.CreateItem
import intelligent_commerce.intelligent_commerce.item.dto.update.*
import intelligent_commerce.intelligent_commerce.item.service.command.ItemCommandService
import intelligent_commerce.intelligent_commerce.item.service.query.ItemQueryService
import intelligent_commerce.intelligent_commerce.logger
import intelligent_commerce.intelligent_commerce.validator.ControllerValidator
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class ItemController @Autowired constructor(
    private val itemQueryService: ItemQueryService,
    private val itemCommandService: ItemCommandService,
    private val controllerValidator: ControllerValidator
) {
    @GetMapping(ItemUrl.ITEM_DETAIL)
    fun itemDetail(@PathVariable(ItemParam.ID) id: Long): ResponseEntity<*> {
        val item = itemQueryService.getItemById(id)
        return ItemResponse.itemDetailSuccess(item)
    }

    @GetMapping(ItemUrl.ITEM_HOME)
    fun itemHome(
        @RequestParam(ItemParam.LAST_ID, required = false) lastId: Long?
    ): ResponseEntity<*> {
        val items = itemQueryService.getAllItems(lastId)
        return ItemResponse.itemHomeSuccess(items)
    }

    @GetMapping(ItemUrl.SHOP_ITEMS)
    fun shopItems(
        @PathVariable(ItemParam.SHOP_ID) shopId: Long,
        @RequestParam(ItemParam.LAST_ID, required = false) lastId: Long?
    ): ResponseEntity<*> {
        val items = itemQueryService.getItemsByShop(shopId, lastId)
        return ItemResponse.shopItemsSuccess(items)
    }

    @GetMapping(ItemUrl.SEARCH_ITEM)
    fun searchItem(
        @RequestParam(ItemParam.KEYWORD) keyword: String,
        @RequestParam(ItemParam.LAST_ID, required = false) lastId: Long?
    ): ResponseEntity<*> {
        val items = itemQueryService.searchItems(keyword, lastId)
        return ItemResponse.searchItemSuccess(items)
    }

    @PostMapping(ItemUrl.CREATE_ITEM)
    fun createItem(
        @RequestBody @Valid createItem: CreateItem,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        itemCommandService.createItem(createItem, identity = principal.name)
        logger().info(ItemControllerLog.CREATE_ITEM_SUCCESS.log)

        return ItemResponse.createItemSuccess()
    }

    @PutMapping(ItemUrl.UPDATE_TITLE)
    fun updateTitle(
        @RequestBody @Valid updateItemTitle: UpdateItemTitle,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        itemCommandService.updateTitle(updateItemTitle, identity = principal.name)
        logger().info(ItemControllerLog.UPDATE_TITLE_SUCCESS.log)

        return ItemResponse.updateTitleSuccess()
    }

    @PutMapping(ItemUrl.UPDATE_CONTENT)
    fun updateContent(
        @RequestBody @Valid updateItemContent: UpdateItemContent,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        itemCommandService.updateContent(updateItemContent, identity = principal.name)
        logger().info(ItemControllerLog.UPDATE_CONTENT_SUCCESS.log)

        return ItemResponse.updateContentSuccess()
    }

    @PutMapping(ItemUrl.UPDATE_PRICE)
    fun updatePrice(
        @RequestBody @Valid updatePrice: UpdatePrice,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        itemCommandService.updatePrice(updatePrice, identity = principal.name)
        logger().info(ItemControllerLog.UPDATE_PRICE_SUCCESS.log)

        return ItemResponse.updatePriceSuccess()
    }

    @PutMapping(ItemUrl.UPDATE_DELIVERY_CHARGE)
    fun updateDeliveryCharge(
        @RequestBody @Valid updateDeliveryCharge: UpdateDeliveryCharge,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        itemCommandService.updateDeliverCharge(updateDeliveryCharge, identity = principal.name)
        logger().info(ItemControllerLog.UPDATE_DELIVERY_CHARGE_SUCCESS.log)

        return ItemResponse.updateDeliveryChargeSuccess()
    }

    @PutMapping(ItemUrl.ADD_REMAINING)
    fun addRemaining(
        @RequestBody @Valid addRemaining: AddRemaining,
        bindingResult: BindingResult,
        principal: Principal
    ): ResponseEntity<*> {
        controllerValidator.validateBinding(bindingResult)

        itemCommandService.addRemaining(addRemaining, identity = principal.name)
        logger().info(ItemControllerLog.ADD_REMAINING_SUCCESS.log)

        return ItemResponse.addRemainingSuccess()
    }

    @DeleteMapping(ItemUrl.DELETE_ITEM)
    fun deleteItem(
        @PathVariable(ItemParam.ID) id: Long,
        principal: Principal
    ): ResponseEntity<*> {
        itemCommandService.deleteItem(identity = principal.name, id)
        logger().info(ItemControllerLog.DELETE_ITEM_SUCCESS.log)

        return ItemResponse.deleteItemSuccess()
    }
}
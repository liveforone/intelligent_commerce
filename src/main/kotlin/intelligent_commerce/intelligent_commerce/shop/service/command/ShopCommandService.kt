package intelligent_commerce.intelligent_commerce.shop.service.command

import intelligent_commerce.intelligent_commerce.item.service.command.ItemCommandService
import intelligent_commerce.intelligent_commerce.member.repository.MemberRepository
import intelligent_commerce.intelligent_commerce.shop.domain.Shop
import intelligent_commerce.intelligent_commerce.shop.dto.request.CreateShop
import intelligent_commerce.intelligent_commerce.shop.dto.update.UpdateShopName
import intelligent_commerce.intelligent_commerce.shop.dto.update.UpdateTel
import intelligent_commerce.intelligent_commerce.shop.repository.ShopRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ShopCommandService @Autowired constructor(
    private val shopRepository: ShopRepository,
    private val memberRepository: MemberRepository,
    private val itemCommandService: ItemCommandService
) {

    fun createShop(createShop: CreateShop, identity: String) {
        Shop.create(memberRepository.findOneByIdentity(identity), createShop.shopName!!, createShop.tel!!)
            .also {
                shopRepository.save(it)
            }
    }

    fun updateShopName(updateShopName: UpdateShopName, identity: String) {
        shopRepository.findOneByIdentity(identity).also {
            it.updateShopName(updateShopName.shopName!!)
        }
    }

    fun updateTel(updateTel: UpdateTel, identity: String) {
        shopRepository.findOneByIdentity(identity).also {
            it.updateTel(updateTel.tel!!)
        }
    }

    fun deleteShop(identity: String) {
        shopRepository.findOneByIdentity(identity)
            .also {
                itemCommandService.deleteItemsByShopId(it.id!!)
                shopRepository.delete(it)
            }
    }
}
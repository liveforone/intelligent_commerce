package intelligent_commerce.intelligent_commerce.shop.service.command

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
    private val memberRepository: MemberRepository
) {

    fun createShop(createShop: CreateShop, identity: String) {
        with(createShop) {
            Shop.create(
                seller = memberRepository.findOneByIdentity(identity),
                shopName!!,
                tel!!
            ).also { shopRepository.save(it) }
        }
    }

    fun updateShopName(updateShopName: UpdateShopName, identity: String) {
        shopRepository.findOneByIdentity(identity)
            .also { it.updateShopName(updateShopName.shopName!!) }
    }

    fun updateTel(updateTel: UpdateTel, identity: String) {
        shopRepository.findOneByIdentity(identity)
            .also { it.updateTel(updateTel.tel!!) }
    }

    fun deleteShop(identity: String) {
        shopRepository.findOneByIdentityNullable(identity)
            ?.also { shopRepository.delete(it) }
    }
}
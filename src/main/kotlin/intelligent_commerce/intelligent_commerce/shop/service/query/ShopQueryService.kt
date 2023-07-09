package intelligent_commerce.intelligent_commerce.shop.service.query

import intelligent_commerce.intelligent_commerce.shop.repository.ShopRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ShopQueryService @Autowired constructor(
    private val shopRepository: ShopRepository
) {

    fun getShopById(id: Long) = shopRepository.findOneDtoById(id)

    fun getShopByIdentity(identity: String) = shopRepository.findOneDtoByIdentity(identity)
}
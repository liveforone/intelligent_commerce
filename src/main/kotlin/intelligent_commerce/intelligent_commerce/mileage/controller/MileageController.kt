package intelligent_commerce.intelligent_commerce.mileage.controller

import intelligent_commerce.intelligent_commerce.mileage.controller.constant.MileageUrl
import intelligent_commerce.intelligent_commerce.mileage.service.query.MileageQueryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class MileageController @Autowired constructor(
    private val mileageQueryService: MileageQueryService,
) {

    @GetMapping(MileageUrl.MILEAGE_INFO)
    fun mileageInfo(principal: Principal): ResponseEntity<*> {
        val mileage = mileageQueryService.getMileageByIdentity(identity = principal.name)
        return ResponseEntity.ok(mileage)
    }
}
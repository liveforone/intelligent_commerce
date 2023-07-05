package intelligent_commerce.intelligent_commerce.mileage.repository

import intelligent_commerce.intelligent_commerce.mileage.domain.Mileage
import org.springframework.data.jpa.repository.JpaRepository

interface MileageRepository : JpaRepository<Mileage, Long>, MileageCustomRepository
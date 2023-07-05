package intelligent_commerce.intelligent_commerce.mileage.domain

object MileagePolicy {
    private const val MILEAGE_POLICY = 0.01

    fun calculateMileage(itemPrice: Long): Long = (itemPrice * MILEAGE_POLICY).toLong()
}
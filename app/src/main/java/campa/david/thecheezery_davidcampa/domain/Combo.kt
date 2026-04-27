package campa.david.thecheezery_davidcampa.domain

data class Combo(
    val id: Int = 0,
    val name: String,
    val price: Float,
)

data class ComboDetail(
    val combo: Combo,
    val products: List<Product>,
)

package campa.david.thecheezery_davidcampa.domain

data class Product(
    val id: Int = 0,
    val name: String,
    val price: Float,
    val type: ProductType,
    val image: String? = null,
    val description: String? = null
)

enum class ProductType(val label: String) {
    HOT_DRINKS("Hot drinks"),
    COLD_DRINKS("Cold drinks"),
    SALTIES("Salties"),
    SWEETS("Sweets")
}
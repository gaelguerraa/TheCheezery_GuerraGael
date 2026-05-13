package campa.david.thecheezery_davidcampa.data.repository

import androidx.room.withTransaction
import campa.david.thecheezery_davidcampa.data.database.AppDatabase
import campa.david.thecheezery_davidcampa.data.database.entity.ComboEntity
import campa.david.thecheezery_davidcampa.data.database.entity.ProductComboEntity
import campa.david.thecheezery_davidcampa.data.database.entity.ProductEntity
import campa.david.thecheezery_davidcampa.data.database.entity.toDomain
import campa.david.thecheezery_davidcampa.data.database.entity.toEntity
import campa.david.thecheezery_davidcampa.domain.Combo
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.domain.ProductType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CheezeryRepository(private val database: AppDatabase) {

    private val productDao = database.productDao()
    private val combosDao = database.combosDao()
    private val productComboDao = database.productComboDao()

    fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts().map { products ->
        products.map { it.toDomain() }
    }

    fun getProductsByType(type: ProductType): Flow<List<Product>> =
        productDao.getProductsByType(type).map { products -> products.map { it.toDomain() } }

    suspend fun insertProduct(product: Product): Long = productDao.insertProduct(product.toEntity())

    fun getAllCombos(): Flow<List<Combo>> = combosDao.getAllCombos().map { combos ->
        combos.map { it.toDomain() }
    }

    suspend fun insertCombo(name: String, price: Float, productIds: List<Int>): Long =
        database.withTransaction {
            val comboId = combosDao.insertCombo(ComboEntity(name = name, price = price))
            if (comboId > 0) {
                productComboDao.insertProductCombos(
                    productIds.map { productId ->
                        ProductComboEntity(productId = productId, comboId = comboId.toInt())
                    },
                )
            }
            comboId
        }

    suspend fun seedProductsIfEmpty() {
        if (productDao.getProductCount() > 0) return

        productDao.insertProducts(defaultProducts)
    }

    private companion object {
        val defaultProducts = listOf(
            ProductEntity(name = "Americano", price = 2.50f, type = ProductType.HOT_DRINKS, description = "Classic hot coffee."),
            ProductEntity(name = "Chai Latte", price = 3.75f, type = ProductType.HOT_DRINKS, description = "Spiced tea with milk."),
            ProductEntity(name = "Cold Brew", price = 3.50f, type = ProductType.COLD_DRINKS, description = "Smooth cold coffee."),
            ProductEntity(name = "Oreo Milkshake", price = 4.50f, type = ProductType.COLD_DRINKS, description = "Creamy cookie milkshake."),
            ProductEntity(name = "Club Sandwich", price = 6.50f, type = ProductType.SALTIES, description = "Toasted sandwich with classic fillings."),
            ProductEntity(name = "Nachos", price = 5.75f, type = ProductType.SALTIES, description = "Crunchy nachos with cheese."),
            ProductEntity(name = "Muffin", price = 2.25f, type = ProductType.SWEETS, description = "Fresh baked muffin."),
            ProductEntity(name = "Strawberry Cheesecake", price = 4.95f, type = ProductType.SWEETS, description = "Cheesecake with strawberry topping."),
        )
    }

}
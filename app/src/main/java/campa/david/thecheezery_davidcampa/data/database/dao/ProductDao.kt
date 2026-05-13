package campa.david.thecheezery_davidcampa.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import campa.david.thecheezery_davidcampa.data.database.entity.ProductEntity
import campa.david.thecheezery_davidcampa.domain.ProductType
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM Products ORDER BY nameProduct")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM Products WHERE typeProduct = :type ORDER BY nameProduct")
    fun getProductsByType(type: ProductType): Flow<List<ProductEntity>>

    @Query("SELECT * FROM Products WHERE idProduct = :productId")
    suspend fun getProductById(productId: Int): ProductEntity?

    @Query("SELECT * FROM Products WHERE nameProduct LIKE '%' || :name || '%' ORDER BY nameProduct")
    fun searchProducts(name: String): Flow<List<ProductEntity>>

    @Query("SELECT COUNT(*) FROM Products")
    suspend fun getProductCount(): Int
}
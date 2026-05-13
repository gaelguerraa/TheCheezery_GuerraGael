package campa.david.thecheezery_davidcampa.data.database.dao

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

    @Query("SELECT * FROM Products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM Products WHERE idProduct = :productId")
    suspend fun getProductById(productId: Int): ProductEntity?

    @Query("SELECT * FROM Products WHERE nameProduct LIKE '%' || :name || '%'")
    fun searchProducts(name: String): Flow<List<ProductEntity>>
}
package campa.david.thecheezery_davidcampa.data.database.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import campa.david.thecheezery_davidcampa.data.database.entity.ProductComboEntity

interface ProductComboDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProductCombo(productCombo: ProductComboEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProductCombos(productCombos: List<ProductComboEntity>)

    @Query("DELETE FROM ProductsCombo WHERE idCombo = :comboId")
    suspend fun deleteProductsForCombo(comboId: Int)
}
package campa.david.thecheezery_davidcampa.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import campa.david.thecheezery_davidcampa.data.database.entity.ComboEntity
import campa.david.thecheezery_davidcampa.data.database.relation.ComboWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface CombosDao {

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertCombo(combo: ComboEntity): Long

@Query("SELECT * FROM Combos ORDER BY nameCombo")
fun getAllCombos(): Flow<List<ComboEntity>>

@Transaction
@Query("SELECT * FROM Combos ORDER BY nameCombo")
fun getCombosWithProducts(): Flow<List<ComboWithProducts>>
}
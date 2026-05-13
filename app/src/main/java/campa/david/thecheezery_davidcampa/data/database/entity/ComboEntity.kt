package campa.david.thecheezery_davidcampa.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import campa.david.thecheezery_davidcampa.domain.Combo

@Entity(tableName = "Combos")
data class ComboEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idCombo")
    val id: Int = 0,

    @ColumnInfo(name = "nameCombo")
    val name: String,

    @ColumnInfo(name = "priceCombo")
    val price: Float,
)

fun ComboEntity.toDomain(): Combo = Combo(
    id = id,
    name = name,
    price = price,
)

fun Combo.toEntity(): ComboEntity = ComboEntity(
    id = id,
    name = name,
    price = price,
)
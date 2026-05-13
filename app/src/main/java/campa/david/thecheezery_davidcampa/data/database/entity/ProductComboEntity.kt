package campa.david.thecheezery_davidcampa.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ProductsCombo",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["idProduct"],
            childColumns = ["idProduct"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ComboEntity::class,
            parentColumns = ["idCombo"],
            childColumns = ["idCombo"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["idProduct"]),
        Index(value = ["idCombo"]),
        Index(value = ["idProduct", "idCombo"], unique = true)
    ]
)
data class ProductComboEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idProductCombo")
    val id: Int = 0
)
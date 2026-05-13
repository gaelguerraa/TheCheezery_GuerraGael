package campa.david.thecheezery_davidcampa.data.database.entity

import androidx.room3.Entity

@Entity(tableName = "Products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idProduct")
    val id: Int = 0,

    @ColumnInfo(name = "nameProduct")
    val name: String,

    @ColumnInfo(name = "priceProduct")
    val price: Float,

    @ColumnInfo(name = "imageProduct")
    val image: String? = null
)
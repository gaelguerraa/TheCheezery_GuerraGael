package campa.david.thecheezery_davidcampa.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.domain.ProductType

@Entity(tableName = "Products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idProduct")
    val id: Int = 0,

    @ColumnInfo(name = "nameProduct")
    val name: String,

    @ColumnInfo(name = "priceProduct")
    val price: Float,

    @ColumnInfo(name = "typeProduct")
    val type: ProductType,

    @ColumnInfo(name = "imageProduct")
    val image: String? = null,

    @ColumnInfo(name = "descriptionProduct")
    val description: String? = null,
)

fun ProductEntity.toDomain(): Product = Product(
    id = id,
    name = name,
    price = price,
    type = type,
    image = image,
    description = description,
)

fun Product.toEntity(): ProductEntity = ProductEntity(
    id = id,
    name = name,
    price = price,
    type = type,
    image = image,
    description = description,
)
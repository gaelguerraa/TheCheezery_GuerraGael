package campa.david.thecheezery_davidcampa.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.Junction
import campa.david.thecheezery_davidcampa.data.database.entity.ComboEntity
import campa.david.thecheezery_davidcampa.data.database.entity.ProductComboEntity
import campa.david.thecheezery_davidcampa.data.database.entity.ProductEntity
import campa.david.thecheezery_davidcampa.data.database.entity.toDomain
import campa.david.thecheezery_davidcampa.domain.ComboDetail


data class ComboWithProducts(
    @Embedded
    val combo: ComboEntity,

    @Relation(
        parentColumn = "idCombo",
        entityColumn = "idProduct",
        associateBy = Junction(
            value = ProductComboEntity::class,
            parentColumn = "idCombo",
            entityColumn = "idProduct",
        ),
    )
    val products: List<ProductEntity>,
)
    fun ComboWithProducts.toDomain(): ComboDetail = ComboDetail(
combo = combo.toDomain(),
products = products.map { it.toDomain() }
)
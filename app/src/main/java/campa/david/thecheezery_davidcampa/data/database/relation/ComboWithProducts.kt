package campa.david.thecheezery_davidcampa.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.Junction


data class ComboWithProducts(
    @Embedded
    val combo: ComboEntity,

    @Relation(
        parentColumn = "idCombo",
        entityColumn = "idProduct",
        associateBy = Junction(
            value = ComboProductsEntity::class,
            parentColumn = "idCombo",
            entityColumn = "idProduct"
        )
    )
    val products: List<ProductEntity>
)
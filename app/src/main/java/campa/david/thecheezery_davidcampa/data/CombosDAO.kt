package campa.david.thecheezery_davidcampa.data


import android.content.ContentValues
import campa.david.thecheezery_davidcampa.data.CheezeryContract.CombosEntry
import campa.david.thecheezery_davidcampa.data.CheezeryContract.ProductsComboEntry
import campa.david.thecheezery_davidcampa.data.CheezeryContract.ProductsEntry
import campa.david.thecheezery_davidcampa.domain.Combo
import campa.david.thecheezery_davidcampa.domain.ComboDetail
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.domain.ProductType

class CombosDAO(private val dbHelper: DatabaseHelper) {

    fun insertCombo(comboName: String, comboPrice: Float, productIds: List<Int>): Long {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        return try {
            val comboValues = ContentValues().apply {
                put(CombosEntry.COLUMN_NAME, comboName)
                put(CombosEntry.COLUMN_PRICE, comboPrice)
            }

            val comboId = db.insert(CombosEntry.TABLE_NAME, null, comboValues)
            if (comboId == -1L) {
                -1L
            } else {
                productIds.forEach { productId ->
                    val relationValues = ContentValues().apply {
                        put(ProductsComboEntry.COLUMN_PRODUCT_ID, productId)
                        put(ProductsComboEntry.COLUMN_COMBO_ID, comboId)
                    }
                    db.insert(ProductsComboEntry.TABLE_NAME, null, relationValues)
                }
                db.setTransactionSuccessful()
                comboId
            }
        } finally {
            db.endTransaction()
        }
    }

    fun getAllCombos(): List<Combo> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            CombosEntry.TABLE_NAME,
            arrayOf(CombosEntry.COLUMN_ID, CombosEntry.COLUMN_NAME, CombosEntry.COLUMN_PRICE),
            null,
            null,
            null,
            null,
            null,
        )

        val combos = mutableListOf<Combo>()
        with(cursor) {
            while (moveToNext()) {
                combos.add(readCombo(this))
            }
        }
        cursor.close()
        return combos
    }

    fun getComboDetails(comboId: Int): ComboDetail? {
        val db = dbHelper.readableDatabase

        val comboCursor = db.query(
            CombosEntry.TABLE_NAME,
            arrayOf(CombosEntry.COLUMN_ID, CombosEntry.COLUMN_NAME, CombosEntry.COLUMN_PRICE),
            "${CombosEntry.COLUMN_ID} = ?",
            arrayOf(comboId.toString()),
            null,
            null,
            null,
        )

        val combo = comboCursor.use {
            if (it.moveToFirst()) {
                readCombo(it)
            } else {
                null
            }
        } ?: return null

        val productsQuery = """
            SELECT p.${ProductsEntry.COLUMN_ID}, p.${ProductsEntry.COLUMN_NAME}, p.${ProductsEntry.COLUMN_PRICE},
                   p.${ProductsEntry.COLUMN_TYPE}, p.${ProductsEntry.COLUMN_IMAGE}, p.${ProductsEntry.COLUMN_DESCRIPTION}
            FROM ${ProductsEntry.TABLE_NAME} p
            INNER JOIN ${ProductsComboEntry.TABLE_NAME} pc
            ON p.${ProductsEntry.COLUMN_ID} = pc.${ProductsComboEntry.COLUMN_PRODUCT_ID}
            WHERE pc.${ProductsComboEntry.COLUMN_COMBO_ID} = ?
        """.trimIndent()

        val productsCursor = db.rawQuery(productsQuery, arrayOf(comboId.toString()))
        val products = mutableListOf<Product>()
        with(productsCursor) {
            while (moveToNext()) {
                products.add(readProduct(this))
            }
        }
        productsCursor.close()

        return ComboDetail(combo = combo, products = products)
    }

    private fun readCombo(cursor: android.database.Cursor): Combo {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(CombosEntry.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(CombosEntry.COLUMN_NAME))
        val price = cursor.getFloat(cursor.getColumnIndexOrThrow(CombosEntry.COLUMN_PRICE))
        return Combo(id = id, name = name, price = price)
    }

    private fun readProduct(cursor: android.database.Cursor): Product {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
        val price = cursor.getFloat(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
        val typeRaw = cursor.getString(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_TYPE))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))

        val type = ProductType.entries.firstOrNull { it.name == typeRaw || it.label == typeRaw }
            ?: ProductType.HOT_DRINKS

        return Product(id = id, name = name, price = price, type = type, image = image, description = description)
    }
}

package campa.david.thecheezery_davidcampa.data

import android.content.ContentValues
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.data.CheezeryContract.ProductsEntry
import campa.david.thecheezery_davidcampa.domain.ProductType

class ProductDAO(private val dbHelper: DatabaseHelper){
    fun insertProduct(product: Product): Long  {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(ProductsEntry.COLUMN_NAME, product.name)
            put(ProductsEntry.COLUMN_PRICE, product.price)
            put(ProductsEntry.COLUMN_TYPE, product.type.name)
            put(ProductsEntry.COLUMN_DESCRIPTION, product.description)
            put(ProductsEntry.COLUMN_IMAGE, product.image)
        }
        return db.insert(ProductsEntry.TABLE_NAME, null, values)
    }

    fun getAllProducts():List<Product>{
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
            arrayOf(
                ProductsEntry.COLUMN_ID,
                ProductsEntry.COLUMN_NAME,
                ProductsEntry.COLUMN_PRICE,
                ProductsEntry.COLUMN_TYPE,
                ProductsEntry.COLUMN_DESCRIPTION,
                ProductsEntry.COLUMN_IMAGE,
                ),
            null, null, null, null, null
        )

        val products = mutableListOf<Product>()
        with(cursor){
            while (moveToNext()){
                products.add(readProduct(this))
            }

        }
        cursor.close()
        return products
    }

    fun getProductsByType(type: ProductType): List<Product> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
            arrayOf(
                ProductsEntry.COLUMN_ID,
                ProductsEntry.COLUMN_NAME,
                ProductsEntry.COLUMN_PRICE,
                ProductsEntry.COLUMN_TYPE,
                ProductsEntry.COLUMN_DESCRIPTION,
                ProductsEntry.COLUMN_IMAGE,
            ),
            "${ProductsEntry.COLUMN_TYPE} = ?",
            arrayOf(type.name),
            null,
            null,
            null,
        )

        val products = mutableListOf<Product>()
        with(cursor) {
            while (moveToNext()) {
                products.add(readProduct(this))
            }
        }
        cursor.close()
        return products
    }

    fun getProductById(productId: Int): Product? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
            arrayOf(
                ProductsEntry.COLUMN_ID,
                ProductsEntry.COLUMN_NAME,
                ProductsEntry.COLUMN_PRICE,
                ProductsEntry.COLUMN_TYPE,
                ProductsEntry.COLUMN_DESCRIPTION,
                ProductsEntry.COLUMN_IMAGE,
            ),
            "${ProductsEntry.COLUMN_ID} = ?",
            arrayOf(productId.toString()),
            null, null, null
        )

        val product: Product? = cursor.use{
            if(it.moveToFirst()){
                readProduct(it)
            } else{
                null
            }
        }
        return product
    }

    private fun readProduct(cursor: android.database.Cursor): Product {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
        val price = cursor.getFloat(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
        val typeRaw = cursor.getString(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_TYPE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
        val type = ProductType.entries.firstOrNull { it.name == typeRaw || it.label == typeRaw }
            ?: ProductType.HOT_DRINKS
        return Product(id, name, price, type, image, description)
    }

}


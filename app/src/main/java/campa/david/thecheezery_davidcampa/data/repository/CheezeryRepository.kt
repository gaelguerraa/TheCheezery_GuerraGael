package campa.david.thecheezery_davidcampa.data.repository

import campa.david.thecheezery_davidcampa.data.database.AppDatabase
import campa.david.thecheezery_davidcampa.data.database.entity.ProductEntity

class CheezeryRepository(private val database: AppDatabase) {

    private val productoDao = database.productoDao()
    private val comboDao = database.comboDao()
    private val comboProductoDao = database.comboProductoDao()


    suspend fun insertProduct(product: ProductEntity) {
        return productDao.insertProduct(product)
    }
    suspend fun insertCombo(combo: ComboEntity) {
        return comboDao.insertCombo(combo)
    }
    suspend fun insertComboProducto(comboProducto: ComboProductoEntity) {
        return comboProductoDao.insertComboProducto(comboProducto)
    }
}
}
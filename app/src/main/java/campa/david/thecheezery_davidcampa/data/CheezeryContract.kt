package campa.david.thecheezery_davidcampa.data

object CheezeryContract {
    object ProductsEntry{
        const val TABLE_NAME = "Products"
        const val COLUMN_ID = "productId"
        const val COLUMN_NAME = "productName"
        const val COLUMN_PRICE = "productPrice"

        const val COLUMN_TYPE = "productType"
        const val COLUMN_IMAGE = "productImage"
        const val COLUMN_DESCRIPTION = "productDescription"

    }

    object CombosEntry {
        const val TABLE_NAME = "Combos"
        const val COLUMN_ID = "comboId"
        const val COLUMN_NAME = "comboName"
        const val COLUMN_PRICE = "comboPrice"
    }

    object ProductsComboEntry {
        const val TABLE_NAME = "ProductsCombo"
        const val COLUMN_ID = "productComboId"
        const val COLUMN_PRODUCT_ID = "productId"
        const val COLUMN_COMBO_ID = "comboId"
    }
}
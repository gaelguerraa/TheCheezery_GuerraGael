package campa.david.thecheezery_davidcampa.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import campa.david.thecheezery_davidcampa.data.DatabaseHelper
import campa.david.thecheezery_davidcampa.data.ProductDAO
import campa.david.thecheezery_davidcampa.domain.Product

class ProductsViewModel {

    class ProductsViewModel(databaseHelper: DatabaseHelper, context: Context): ViewModel(){

        val dao = ProductDAO(databaseHelper)
        val contextL = context

        fun onSaveProduct(name: String, price: Float, image: String, description: String) {
            val product = Product(name = name, price = price, image = image, description = description)

            val idNewProduct = dao.insertProduct(product)

            if(idNewProduct == -1L){
                Toast.makeText(contextL, "Error inserting product", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(contextL, "Product inserted successfully", Toast.LENGTH_SHORT).show()
            }
        }

        fun getAllProducts(): List<Product>{
            return dao.getAllProducts()
        }

        fun getProductById(productId: Int): Product?{
            return dao.getProductById(productId)
        }
    }
}
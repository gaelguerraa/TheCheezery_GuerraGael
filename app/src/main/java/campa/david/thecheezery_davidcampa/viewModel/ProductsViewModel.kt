package campa.david.thecheezery_davidcampa.viewModel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import campa.david.thecheezery_davidcampa.data.DatabaseHelper
import campa.david.thecheezery_davidcampa.data.ProductDAO
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.domain.ProductType
import kotlinx.coroutines.launch

class ProductViewModel (private val dao: ProductDAO, private val context: Context): ViewModel(){

    var productsListState by mutableStateOf(value = listOf<Product>())
        private set

    var selectedTypeState by mutableStateOf<ProductType?>(null)
        private set

    init {
        viewModelScope.launch {
            getAllProducts()
        }
    }

    fun saveProduct(product: Product){

        val newProduct = dao.insertProduct(product)
        if (newProduct != -1L){
            Toast.makeText(context, "Producto guardado", Toast.LENGTH_SHORT).show()
            refreshProducts()

        }else{
            Toast.makeText(context, "Hubo un error al guardar", Toast.LENGTH_SHORT).show()
        }

    }

    fun filterByType(type: ProductType){
        selectedTypeState = type
        productsListState = dao.getProductsByType(type)
    }

    fun getAllProducts(){
        selectedTypeState = null
        productsListState = dao.getAllProducts()
    }

    private fun refreshProducts() {
        val selectedType = selectedTypeState
        if (selectedType != null) {
            productsListState = dao.getProductsByType(selectedType)
        } else {
            productsListState = dao.getAllProducts()
        }
    }

}
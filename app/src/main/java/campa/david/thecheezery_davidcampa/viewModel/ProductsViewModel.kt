package campa.david.thecheezery_davidcampa.viewModel


import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import campa.david.thecheezery_davidcampa.data.repository.CheezeryRepository
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.domain.ProductType
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: CheezeryRepository,
    private val context: Context,
) : ViewModel() {

    var productsListState by mutableStateOf<List<Product>>(emptyList())
        private set

    var selectedTypeState by mutableStateOf<ProductType?>(null)
        private set

    private var productsJob: Job? = null

    init {
        viewModelScope.launch {
            repository.seedProductsIfEmpty()
        }
        getAllProducts()
    }

    fun saveProduct(product: Product) {
        viewModelScope.launch {
            val newProduct = repository.insertProduct(product)
            if (newProduct != -1L) {
                Toast.makeText(context, "Producto guardado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Hubo un error al guardar", Toast.LENGTH_SHORT).show()
            }
        } // ← faltaba esta
    }

    fun filterByType(type: ProductType) {
        selectedTypeState = type
        productsJob?.cancel()
        productsJob = viewModelScope.launch {
            repository.getProductsByType(type).collect { products ->
                productsListState = products
            }
        }
    }

    fun getAllProducts() {
        selectedTypeState = null
        productsJob?.cancel()
        productsJob = viewModelScope.launch {
            repository.getAllProducts().collect { products ->
                productsListState = products
            }
        }
    }

    private fun refreshProducts() {
        productsJob?.cancel()
        productsJob = viewModelScope.launch {
            repository.getAllProducts().collect { products ->
                productsListState = products
            }
        }
    }

}
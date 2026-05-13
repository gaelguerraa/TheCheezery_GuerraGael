package campa.david.thecheezery_davidcampa.viewModel


import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import campa.david.thecheezery_davidcampa.data.repository.CheezeryRepository
import campa.david.thecheezery_davidcampa.domain.Combo
import kotlinx.coroutines.launch

class CombosViewModel(
    private val repository: CheezeryRepository,
    private val context: Context,
) : ViewModel() {

    var combosState by mutableStateOf<List<Combo>>(emptyList())
        private set

    init {
        getAllCombos()
    }

    fun saveCombo(name: String, price: Float, productIds: List<Int>) {
        viewModelScope.launch {
            val comboId = repository.insertCombo(name, price, productIds)
            if (comboId != -1L) {
                Toast.makeText(context, "Combo guardado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Hubo un error al guardar combo", Toast.LENGTH_SHORT).show()
            }
        } }

        fun getAllCombos() {
            viewModelScope.launch {
                repository.getAllCombos().collect { combos ->
                    combosState = combos
                }
            }
        }
    }


package campa.david.thecheezery_davidcampa.viewModel


import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import campa.david.thecheezery_davidcampa.data.CombosDAO
import campa.david.thecheezery_davidcampa.domain.Combo

class CombosViewModel(
    private val combosDAO: CombosDAO,
    private val context: Context,
) : ViewModel() {

    var combosState by mutableStateOf<List<Combo>>(emptyList())
        private set

    init {
        getAllCombos()
    }

    fun saveCombo(name: String, price: Float, productIds: List<Int>) {
        val comboId = combosDAO.insertCombo(name, price, productIds)
        if (comboId != -1L) {
            Toast.makeText(context, "Combo guardado", Toast.LENGTH_SHORT).show()
            getAllCombos()
        } else {
            Toast.makeText(context, "Hubo un error al guardar combo", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllCombos() {
        combosState = combosDAO.getAllCombos()
    }
}

package campa.david.thecheezery_davidcampa.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.ui.theme.Pinky
import campa.david.thecheezery_davidcampa.viewModel.CombosViewModel
import campa.david.thecheezery_davidcampa.viewModel.ProductViewModel

@Composable
fun AddComboScreen(
    innerPadding: PaddingValues,
    productViewModel: ProductViewModel,
    combosViewModel: CombosViewModel,
    onComboSaved: () -> Unit = {},
) {
    var comboName by remember { mutableStateOf("") }
    var comboPriceField by remember { mutableStateOf("") }
    val selectedProductIds = remember { mutableStateListOf<Int>() }

    val allProducts = productViewModel.productsListState

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Add a new combo", color = Pinky, fontSize = 30.sp, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = comboName,
            onValueChange = { comboName = it },
            label = { Text(text = "Combo name") },
        )
        OutlinedTextField(
            value = comboPriceField,
            onValueChange = { comboPriceField = it },
            label = { Text(text = "Combo price") },
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Select products", modifier = Modifier.fillMaxWidth())

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(allProducts) { product ->
                ProductSelectionRow(
                    product = product,
                    isSelected = selectedProductIds.contains(product.id),
                    onToggle = { checked ->
                        if (checked) {
                            if (!selectedProductIds.contains(product.id)) {
                                selectedProductIds.add(product.id)
                            }
                        } else {
                            selectedProductIds.remove(product.id)
                        }
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                combosViewModel.saveCombo(
                    name = comboName,
                    price = comboPriceField.toFloatOrNull() ?: 0f,
                    productIds = selectedProductIds.toList(),
                )
                onComboSaved()
            },
        ) {
            Text(text = "Save combo")
        }
    }
}

@Composable
private fun ProductSelectionRow(
    product: Product,
    isSelected: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = "${product.name} (${product.type.label})")
        Checkbox(checked = isSelected, onCheckedChange = onToggle)
    }
}

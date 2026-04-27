package campa.david.thecheezery_davidcampa.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import campa.david.thecheezery_davidcampa.ui.theme.Pinky
import campa.david.thecheezery_davidcampa.viewModel.ProductViewModel
import campa.david.thecheezery_davidcampa.R
import campa.david.thecheezery_davidcampa.domain.Product
import campa.david.thecheezery_davidcampa.domain.ProductType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(innerPadding: PaddingValues,
                     viewModel: ProductViewModel,
                     onProductSaved: () -> Unit = {}){

    var name by remember { mutableStateOf(value = "") }
    var priceField by remember { mutableStateOf(value = "") }
    var description by remember { mutableStateOf(value = "") }
    var expanded by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf(ProductType.HOT_DRINKS) }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .padding(horizontal = 16.dp)){

        Text(text = "Add a new product", color = Pinky, fontSize = 30.sp, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(height = 30.dp))
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = {Text(text = "Name")},
        )
        OutlinedTextField(
            value = priceField,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {priceField = it},
            label = {Text(text = "Price")},
            trailingIcon = { Image(
                painter = painterResource(id = R.drawable.dollar_24),
                contentDescription = "Dolar icon",
            )}
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedType.label,
                onValueChange = {},
                label = { Text("Type") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                ProductType.entries.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type.label) },
                        onClick = {
                            selectedType = type
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = description,
            onValueChange = {description = it},
            label = {Text(text = "Description")},
        )

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                val price = priceField.toFloatOrNull() ?: 0f
                viewModel.saveProduct(
                    Product(
                        name = name,
                        price = price,
                        type = selectedType,
                        description = description,
                    )
                )
                onProductSaved()
            }
        ) {
            Text(text = "Save product")
        }
    }
}


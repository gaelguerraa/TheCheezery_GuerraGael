package campa.david.thecheezery_davidcampa.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import campa.david.thecheezery_davidcampa.R
import campa.david.thecheezery_davidcampa.components.ProductForm
import campa.david.thecheezery_davidcampa.domain.Product

@Composable
fun ProductsScreen(
    innerPadding: PaddingValues,
    products: List<Product>,
    onSaveProduct: (name: String, price: Float, image: String, description: String) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProductForm(onSaveProduct)
        Spacer(Modifier.height(20.dp))
        Text("Products", textAlign = TextAlign.Center, fontSize = 30.sp)
        LazyColumn(Modifier.fillMaxWidth()) {
            items(products) { product ->
                Row {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = null
                    )
                    Column {
                        Text(product.name)
                        Text("${product.description}")
                    }

                    Text("$${product.price}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductsScreenPreview() {
    ProductsScreen(
        PaddingValues(20.dp),
        listOf(Product(1, "Latte", 40f, "", "")),
        { _, _, _, _ -> }
    )
}
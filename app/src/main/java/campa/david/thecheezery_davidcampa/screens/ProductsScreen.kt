package campa.david.thecheezery_davidcampa.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
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
import campa.david.thecheezery_davidcampa.viewModel.ProductViewModel

@Composable
fun ShowProducts(viewModel: ProductViewModel, title: String){

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(space = 12.dp)) {
            items(items = viewModel.productsListState){ product ->

                ProductItem(product)

            }
        }
    }
}

@Composable
fun ProductItem(product: Product){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = R.drawable.muffin), contentDescription = "muffin")
        Column(modifier = Modifier.fillMaxWidth(fraction = 0.7f)){
            Text(text = product.name)
            Text(text = product.description.orEmpty())
            Text(text = product.type.label, style = MaterialTheme.typography.labelMedium)
        }
        Text(text = "$${product.price}")
    }
}


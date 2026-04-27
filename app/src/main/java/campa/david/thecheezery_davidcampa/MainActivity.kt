package campa.david.thecheezery_davidcampa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import campa.david.thecheezery_davidcampa.data.CombosDAO
import campa.david.thecheezery_davidcampa.data.DatabaseHelper
import campa.david.thecheezery_davidcampa.data.ProductDAO
import campa.david.thecheezery_davidcampa.domain.AppScreen
import campa.david.thecheezery_davidcampa.domain.ProductType
import campa.david.thecheezery_davidcampa.screens.AddComboScreen
import campa.david.thecheezery_davidcampa.screens.AddProductScreen
import campa.david.thecheezery_davidcampa.screens.CheezeryMenuScreen
import campa.david.thecheezery_davidcampa.screens.CheezeryWelcomeScreen
import campa.david.thecheezery_davidcampa.screens.ShowProducts
import campa.david.thecheezery_davidcampa.ui.theme.Pinky
import campa.david.thecheezery_davidcampa.ui.theme.TheCheezery_DavidCampaTheme
import campa.david.thecheezery_davidcampa.viewModel.CombosViewModel
import campa.david.thecheezery_davidcampa.viewModel.ProductViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheCheezery_DavidCampaTheme {
                CheezeryApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheezeryApp() {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }
    val productViewModel = remember { ProductViewModel(ProductDAO(dbHelper), context) }
    val combosViewModel = remember { CombosViewModel(CombosDAO(dbHelper), context) }
    var currentScreen by remember { mutableStateOf(AppScreen.WELCOME) }
    var productsTitle by remember { mutableStateOf("Products") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentScreen != AppScreen.WELCOME) {
                TopAppBar(
                    title = { Text(text = "The Cheezery") },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Pinky, titleContentColor = Color.White),
                    navigationIcon = {
                        if (currentScreen != AppScreen.MENU) {
                            IconButton(onClick = { currentScreen = AppScreen.MENU }) {
                                Icon(
                                    painter = painterResource(id = android.R.drawable.ic_media_previous),
                                    contentDescription = "Back to menu",
                                    tint = Color.White,
                                )
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        when (currentScreen) {
            AppScreen.WELCOME -> CheezeryWelcomeScreen(onGetStarted = { currentScreen = AppScreen.MENU })
            AppScreen.MENU -> CheezeryMenuScreen(onCategoryClick = { category ->
                val selectedType = when (category) {
                    ProductType.HOT_DRINKS.label -> ProductType.HOT_DRINKS
                    ProductType.COLD_DRINKS.label -> ProductType.COLD_DRINKS
                    ProductType.SALTIES.label -> ProductType.SALTIES
                    ProductType.SWEETS.label -> ProductType.SWEETS
                    "Add new product" -> {
                        currentScreen = AppScreen.ADD_PRODUCT
                        null
                    }
                    "Add combo" -> {
                        productViewModel.getAllProducts()
                        currentScreen = AppScreen.ADD_COMBO
                        null
                    }
                    else -> null
                }

                if (selectedType != null) {
                    productViewModel.filterByType(selectedType)
                    productsTitle = selectedType.label
                    currentScreen = AppScreen.PRODUCTS
                }
            })
            AppScreen.PRODUCTS -> ShowProducts(viewModel = productViewModel, title = productsTitle)
            AppScreen.ADD_PRODUCT -> AddProductScreen(
                innerPadding = innerPadding,
                viewModel = productViewModel,
                onProductSaved = { currentScreen = AppScreen.MENU }
            )
            AppScreen.ADD_COMBO -> AddComboScreen(
                innerPadding = innerPadding,
                productViewModel = productViewModel,
                combosViewModel = combosViewModel,
                onComboSaved = { currentScreen = AppScreen.MENU },
            )
        }
    }
}
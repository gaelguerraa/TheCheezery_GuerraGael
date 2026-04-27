package campa.david.thecheezery_davidcampa.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import campa.david.thecheezery_davidcampa.R
import campa.david.thecheezery_davidcampa.ui.theme.Brighter_Pink
import campa.david.thecheezery_davidcampa.ui.theme.Less_Purple
import campa.david.thecheezery_davidcampa.ui.theme.Pinky
import campa.david.thecheezery_davidcampa.ui.theme.Very_purple


val firstGradient  = Brush.verticalGradient(listOf(Brighter_Pink, Pinky))
val secondGradient = Brush.verticalGradient(listOf(Pinky, Less_Purple))
val thirdGradient  = Brush.verticalGradient(listOf(Less_Purple, Very_purple))

data class MenuCategory(val label: String, val gradient: Brush)

val menuItems = listOf(
    MenuCategory("Hot drinks",       firstGradient),
    MenuCategory("Cold drinks",      firstGradient),
    MenuCategory("Salties",          secondGradient),
    MenuCategory("Sweets",           secondGradient),
    MenuCategory("Combos",           thirdGradient),
    MenuCategory("Add new product",  thirdGradient)
)

@Composable
fun CheezeryMenuScreen(onCategoryClick: (String) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.grupo2),
            contentDescription = "The Cheezery logo",
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .aspectRatio(2.2f),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            menuItems.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    rowItems.forEach { item ->
                        MenuCell(
                            label    = item.label,
                            gradient = item.gradient,
                            modifier = Modifier.weight(1f),
                            onClick  = { onCategoryClick(item.label) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MenuCell(
    label:    String,
    gradient: Brush,
    modifier: Modifier = Modifier,
    onClick:  () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(72.dp)
            .background(gradient)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text       = label,
            color      = Color.White,
            fontSize   = 15.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign  = TextAlign.Center,
            modifier   = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheezeryMenuScreenPreview() {
    CheezeryMenuScreen()
}


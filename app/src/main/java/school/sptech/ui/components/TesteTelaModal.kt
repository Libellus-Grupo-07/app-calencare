import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.ui.components.FormFieldWithLabel
import school.sptech.R
import school.sptech.ui.theme.RoxoNubank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModal() {
    var sliderPosition by remember { mutableStateOf(5f..22f) }
    var selectedCategory by remember { mutableStateOf("Corpo") }
    var reposicaoData by remember { mutableStateOf("") }
    var retiradaData by remember { mutableStateOf("") }
    var validadeData by remember { mutableStateOf("") }
    val categocias = remember {
        mutableListOf("Pele", "Corpo", "Cabelos", "Unha")
    }

    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = rememberModalBottomSheetState(),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Filtro",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Quantidade Disponível - Range Slider
        Text(text = "Quantidade Disponível", fontSize = 16.sp, color = Color.Gray)
        RangeSlider(
            value = sliderPosition,
            onValueChange = { range -> sliderPosition = range },
            valueRange = 1f..100f,
            steps = 5,
            colors = SliderDefaults.colors(
                thumbColor = RoxoNubank,
                activeTrackColor = RoxoNubank,
                inactiveTrackColor = Color.LightGray
            )
        )
        Text(text = "${sliderPosition.start.toInt()} - ${sliderPosition.endInclusive.toInt()}")

        Spacer(modifier = Modifier.height(16.dp))

        // Categorias
        Text(text = "Categorias", fontSize = 16.sp, color = Color.Gray)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            LazyRow {
                items(categocias.size) {
                    Button(
                        onClick = { selectedCategory = categocias[it] },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == categocias[it]) RoxoNubank else Color.LightGray,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text(text = categocias[it])
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    FormFieldWithLabel(
        value = reposicaoData,
        onValueChange = { reposicaoData = it },
        label = "Data de Reposição",
        isDateInput = true,
        isSmallInput = true
    )
    Spacer(modifier = Modifier.height(16.dp))
    FormFieldWithLabel(
        value = retiradaData,
        onValueChange = { retiradaData = it },
        label = "Data de Retirada",
        isDateInput = true,
        isSmallInput = true
    )
    Spacer(modifier = Modifier.height(16.dp))
    FormFieldWithLabel(
        value = validadeData,
        onValueChange = { validadeData = it },
        label = "Data de Validade",
        isDateInput = true,
        isSmallInput = true
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFilterModal() {
    FilterModal()
}

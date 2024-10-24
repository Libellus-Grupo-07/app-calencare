package school.sptech.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.CinzaOpacidade35
import school.sptech.ui.theme.CinzaOpacidade7
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.RoxoNubankOpacidade15

@Composable
fun SeletorData(
    mesSelecionado: String,
    anoSelecionado: Int,
    onDismissRequest: () -> Unit,
    onConfirm: (String, Int) -> Unit,
) {
    CustomMonthYearPickerDialog(
        mesSelecionado = mesSelecionado,
        anoSelecionado = anoSelecionado,
        onDismissRequest = onDismissRequest,
        onConfirm = { mes, ano ->
            onConfirm(mes, ano)
        }
    )
}
@Composable
fun SelectableDatesRow(
    dates: List<String>,
    onDateSelected: (String) -> Unit,
    selectedDate: String? = null
) {
    var currentDate by remember { mutableStateOf(selectedDate) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(11.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Selecione uma data",
                color = Cinza,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dates) { date ->
                DateItem(
                    date = date,
                    isSelected = date == currentDate,
                    onClick = {
                        currentDate = date
                        onDateSelected(date)
                    }
                )
            }
        }
    }
}
@Composable
fun DateItem(
    date: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable { onClick() }
            .background(
                color = if (isSelected) RoxoNubankOpacidade15 else CinzaOpacidade7,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = date,
            color = if (isSelected) RoxoNubank else CinzaOpacidade35,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}
package school.sptech.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.CinzaOpacidade35
import school.sptech.ui.theme.CinzaOpacidade7
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.RoxoNubankOpacidade15
import school.sptech.ui.theme.VerdeOpacidade15

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
fun DateItem(
    date: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(if (isSelected) RoxoNubankOpacidade15 else CinzaOpacidade7)
            .padding(10.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = date,
            color = if (isSelected) RoxoNubank else CinzaOpacidade35
        )
    }
}

@Composable
fun SelectableDatesRow(
    dates: List<String>,
    selectedDates: List<String>,
    onDateSelected: (String) -> Unit
) {
    val totalSelected = selectedDates.size

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$totalSelected produto${if (totalSelected > 1) "s" else ""} ",
                color = RoxoNubank,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Text(
                text = "com a data de validade selecionada.",
                color = Color.Black,
                fontSize = 12.sp
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
                    isSelected = selectedDates.contains(date),
                    onClick = {
                        onDateSelected(date)
                    }
                )
            }
        }
    }
}
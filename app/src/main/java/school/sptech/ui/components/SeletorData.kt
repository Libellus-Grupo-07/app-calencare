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
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.CinzaOpacidade7
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.RoxoNubankOpacidade15
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal

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
    qtdEstoqueData: Int = 0,
    isRepor: Boolean = false,
    selectedDate: String? = null
) {
    var currentDate by remember { mutableStateOf(selectedDate) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            LabelInput(label = "Data de Validade")

            if(isRepor){
                TextButton(
                    onClick = { /*TODO*/ },
                    contentPadding = PaddingValues(horizontal = 4.dp),
                ) {
                    Text(
                        text = "Adicionar Data",
                        fontFamily = fontFamilyPoppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        letterSpacing = letterSpacingPrincipal,
                        color = Preto
                    )
                }
            }

        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                buildAnnotatedString {
                     withStyle(
                         SpanStyle(
                             color = RoxoNubank,
                             fontFamily = fontFamilyPoppins,
                             letterSpacing = letterSpacingPrincipal,
                             fontSize = 10.5.sp,
                             fontWeight = FontWeight.ExtraBold
                         )
                    ) {
                        append("$qtdEstoqueData ${if (qtdEstoqueData == 1) "produto" else "produtos"} ")
                    }
                    withStyle(SpanStyle(
                        color = Cinza,
                        fontFamily = fontFamilyPoppins,
                        letterSpacing = letterSpacingPrincipal,
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.5.sp,
                    )
                    ) {
                        append("com a data de validade selecionada.")
                    }
                },
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
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = if (isSelected) RoxoNubankOpacidade15 else CinzaOpacidade7,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = date,
            color = if (isSelected) RoxoNubank else Cinza,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 12.sp,
            fontFamily = fontFamilyPoppins,
            letterSpacing = letterSpacingPrincipal,
            textAlign = TextAlign.Center
        )
    }
}
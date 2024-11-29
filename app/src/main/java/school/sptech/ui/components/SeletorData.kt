package school.sptech.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import formatarData
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.CinzaOpacidade7
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.RoxoNubankOpacidade15
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.theme.letterSpacingSecundaria

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
    onClickAdicionarData: () -> Unit = {},
    qtdEstoqueData: Int = 0,
    isRepor: Boolean = false,
    selectedDate: String? = null,
    isMedium: Boolean = false,
    dataError: Boolean = false
) {
    var currentDate by remember { mutableStateOf(selectedDate) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            LabelInput(label = "Data de Validade", isMediumInput = isMedium)

            if (isRepor) {
                Text(
                    modifier = Modifier.clickable { onClickAdicionarData() },
                    text = "Adicionar Data",
                    fontFamily = fontFamilyPoppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isMedium) 9.sp else 10.sp,
                    letterSpacing = letterSpacingPrincipal,
                    color = Preto
                )
            }
        }

        Spacer(modifier = Modifier.height(if (isMedium) 4.dp else 8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            if (dates.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nenhuma data de validade disponível",
                        color = Cinza,
                        fontFamily = fontFamilyPoppins,
                        letterSpacing = letterSpacingSecundaria,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
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
                        withStyle(
                            SpanStyle(
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
        }

        Spacer(modifier = Modifier.height(if (isMedium) 4.dp else 8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dates) { date ->
                DateItem(
                    date = formatarData(date),
                    isSelected = date == currentDate,
                    onClick = {
                        //if(date != currentDate){
                        currentDate = date
                        onDateSelected(date)
                        //}
                    }
                )
            }
        }

        if (dataError) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Selecione uma data de validade",
                color = Vermelho,
                fontFamily = fontFamilyPoppins,
                letterSpacing = letterSpacingPrincipal,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
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
            .padding(horizontal = 13.dp, vertical = 5.dp)
    ) {
        Text(
            text = date,
            color = if (isSelected) RoxoNubank else Cinza,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 11.sp,
            fontFamily = fontFamilyPoppins,
            letterSpacing = letterSpacingPrincipal,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthYearPicker(
    selectedMonth: String,
    selectedYear: Int,
    onMonthSelected: (String) -> Unit,
    onYearSelected: (Int) -> Unit,
    currentYear: Int = 2024,
    yearRange: IntRange = (1900..currentYear)
) {
    var expanded by remember { mutableStateOf(false) }
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    Column {
        // Month Picker
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedMonth,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Month") },
                trailingIcon = {
                    Icon(Icons.Filled.ArrowDropDown, null)
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                months.forEach { month ->
                    DropdownMenuItem(onClick = {
                        onMonthSelected(month)
                        expanded = false
                    },
                        text = {
                            Text(text = month)
                        })
                }
            }
        }

        // Year Picker
        LazyColumn(
            modifier = Modifier.height(200.dp) // Set an appropriate height
        ) {
            items(yearRange.toList()) { year ->
                Text(
                    text = year.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onYearSelected(year) }
                )
            }
        }
    }
}
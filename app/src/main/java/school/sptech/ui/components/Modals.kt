package school.sptech.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import formatarDataDatePicker
import school.sptech.R
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.PretoOpacidade25
import school.sptech.ui.theme.RoxoNubank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomMonthYearPickerDialog(
    mesSelecionado: String,
    anoSelecionado: Int,
    onDismissRequest: () -> Unit,
    onConfirm: (String, Int) -> Unit
) {
    var mes by remember { mutableStateOf(mesSelecionado) }
    var ano by remember { mutableStateOf(anoSelecionado) }
    var expandedMes by remember { mutableStateOf(false) }
    var expandedAno by remember { mutableStateOf(false) }

    val meses = listOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")
    val anos = (2020..2100).toList()

    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        onDismissRequest = onDismissRequest,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TituloLarge(titulo = "Selecione o Mês e Ano")
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onDismissRequest)
                {
                    Icon(Icons.Filled.Close, contentDescription = "Fechar", tint = Preto)
                }
            }
        },
        text = {
            Column {
                DropdownBox(mes, expandedMes, { expandedMes = true }, { mes = it; expandedMes = false }, meses)
                Spacer(modifier = Modifier.height(16.dp))
                DropdownBox(ano.toString(), expandedAno, { expandedAno = true }, { ano = it.toInt(); expandedAno = false }, anos.map { it.toString() })
            }
        },
        confirmButton = {
            ButtonBackground(titulo = stringResource(id = R.string.salvar), cor = RoxoNubank, onClick = { onConfirm(mes, ano) })
        },
        dismissButton = {
            ButtonCancelar(onClick = onDismissRequest)
        },
        containerColor = Branco

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(dateSelected:Long, onDismiss: () -> Unit, onDateSelected: (Long?) -> Unit) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = if(dateSelected > 0) dateSelected else null,
        initialDisplayedMonthMillis = if(dateSelected > 0) dateSelected else null,
        initialDisplayMode = DisplayMode.Picker,
        selectableDates = DatePickerDefaults.AllDates
    )

    DatePickerDialog(
        modifier = Modifier.fillMaxHeight(0.7f),
        onDismissRequest = onDismiss,
        confirmButton = {
            ButtonBackground(
                titulo = stringResource(id = R.string.salvar),
                cor = RoxoNubank,
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                })
        },
        dismissButton = {
            ButtonCancelar(onClick = onDismiss)
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        colors = DatePickerDefaults.colors(
            containerColor = Branco
        )
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = Branco,
                titleContentColor = Preto,
                subheadContentColor = Preto,
                navigationContentColor = Cinza,
                yearContentColor = Cinza,
                selectedYearContentColor = Branco,
                selectedYearContainerColor = RoxoNubank,
                dayContentColor = Cinza,
                weekdayContentColor = Preto,
                selectedDayContentColor = Branco,
                selectedDayContainerColor = RoxoNubank,
                todayDateBorderColor = RoxoNubank,
                headlineContentColor = RoxoNubank,
                dividerColor = PretoOpacidade25,
                todayContentColor = RoxoNubank,
            ),
            title = {
                Box(modifier = Modifier.padding(start = 24.dp, top = 32.dp, bottom = 0.dp))
                {
                    TituloLarge(titulo = "Selecione a Data")
                }
            },
            headline = {
                Box(modifier = Modifier.padding(horizontal = 24.dp))
                {
                    TextoButtonLarge(
                        texto = formatarDataDatePicker(data = datePickerState.selectedDateMillis)
                    )
                }
            },
        )
    }
}
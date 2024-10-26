package school.sptech.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import formatarDataDatePicker
import kotlinx.coroutines.launch
import school.sptech.R
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Laranja
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.PretoOpacidade25
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Verde
import school.sptech.ui.theme.VerdeOpacidade15
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.VermelhoOpacidade15
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.viewModel.ReporProdutoViewModel

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

    val meses = listOf(
        "Janeiro",
        "Fevereiro",
        "Março",
        "Abril",
        "Maio",
        "Junho",
        "Julho",
        "Agosto",
        "Setembro",
        "Outubro",
        "Novembro",
        "Dezembro"
    )
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
                    onClick = onDismissRequest
                )
                {
                    Icon(Icons.Filled.Close, contentDescription = "Fechar", tint = Preto)
                }
            }
        },
        text = {
            Column {
                DropdownBox(
                    mes,
                    expandedMes,
                    { expandedMes = true },
                    { mes = it; expandedMes = false },
                    meses
                )
                Spacer(modifier = Modifier.height(16.dp))
                DropdownBox(
                    ano.toString(),
                    expandedAno,
                    { expandedAno = true },
                    { ano = it.toInt(); expandedAno = false },
                    anos.map { it.toString() })
            }
        },
        confirmButton = {
            ButtonBackground(
                titulo = stringResource(id = R.string.salvar),
                cor = RoxoNubank,
                onClick = { onConfirm(mes, ano) })
        },
        dismissButton = {
            ButtonCancelar(onClick = onDismissRequest)
        },
        containerColor = Branco

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(dateSelected: Long, onDismiss: () -> Unit, onDateSelected: (Long?) -> Unit) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = if (dateSelected > 0) dateSelected else null,
        initialDisplayedMonthMillis = if (dateSelected > 0) dateSelected else null,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductModal(
    title: String,
    buttonColor: Color,
    buttonText: String,
    produto: String,
    quantidadeEstoque: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    viewModel: ReporProdutoViewModel = ReporProdutoViewModel(),
    datesFromBackend: List<String>
) {
    var data by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Branco,
        titleContentColor = buttonColor,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        title = {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TituloLarge(titulo = title, defaultColor = false)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(24.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Preto,
                        containerColor = Color.Transparent,
                    )
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "Fechar")
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                FormFieldWithLabel(
                    value = produto,
                    onValueChange = {},
                    label = "Produto",
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                if (datesFromBackend.isNotEmpty() && datesFromBackend[0].isNotEmpty()) {
                    SelectableDatesRow(
                        dates = datesFromBackend,
                        onDateSelected = { data = it }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LabelInput(label = "Quantidade")
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { viewModel.diminuirQuantidade() }) {
                        Image(
                            bitmap = ImageBitmap.imageResource(id = R.mipmap.image_remover_menos),
                            contentDescription = "Remover Produto"
                        )
                    }
                    Text(
                        text = "${viewModel.quantidade.value} produtos",
                        fontFamily = fontFamilyPoppins,
                        color = Preto,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = letterSpacingPrincipal
                    )
                    IconButton(
                        onClick = { viewModel.aumentarQuantidade() },
                        colors = IconButtonColors(
                            containerColor = VerdeOpacidade15,
                            contentColor = Verde,
                            disabledContentColor = Verde,
                            disabledContainerColor = VerdeOpacidade15
                        )
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Aumentar",
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row() {
                    LabelInput(label = "Total em Estoque: ")
                    LabelInput(
                        label = "$quantidadeEstoque produtos",
                        color = Laranja
                    )
                }
            }
        },
        confirmButton = {
            ButtonBackground(
                titulo = buttonText,
                cor = buttonColor,
                onClick = { onConfirm(viewModel.quantidade.value) }
            )
        },
        dismissButton = {
            ButtonCancelar(onClick = onDismiss)
        }
    )
}

@Composable
fun ReporProductModal(
    produto: String,
    quantidadeEstoque: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    viewModel: ReporProdutoViewModel = ReporProdutoViewModel(),
    datesFromBackend: List<String>
) {
    ProductModal(
        title = "Repor Produto",
        buttonColor = Verde,
        buttonText = stringResource(id = R.string.repor),
        produto = produto,
        quantidadeEstoque = quantidadeEstoque,
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        viewModel = viewModel,
        datesFromBackend = datesFromBackend
    )
}

@Composable
fun RetirarProductModal(
    produto: String,
    quantidadeEstoque: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    viewModel: ReporProdutoViewModel = ReporProdutoViewModel(),
    datesFromBackend: List<String>
) {
    ProductModal(
        title = "Retirar Produto",
        buttonColor = Vermelho,
        buttonText = stringResource(id = R.string.retirar),
        produto = produto,
        quantidadeEstoque = quantidadeEstoque,
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        viewModel = viewModel,
        datesFromBackend = datesFromBackend
    )
}

@Composable
fun AlertError(msg:String){
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ExtendedFloatingActionButton(
        text = { Text("Deu merda!!! erro => $msg") },
        icon = { Icon(Icons.Filled.Clear, contentDescription = "") },
        onClick = {
            scope.launch {
                val result = snackbarHostState
                    .showSnackbar(
                        message = "Snackbar",
                        actionLabel = "Action",
                        // Defaults to SnackbarDuration.Short
                        duration = SnackbarDuration.Indefinite
                    )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        /* Handle snackbar action performed */
                    }

                    SnackbarResult.Dismissed -> {
                        /* Handle snackbar dismissed */
                    }
                }
            }
        }
    )
}
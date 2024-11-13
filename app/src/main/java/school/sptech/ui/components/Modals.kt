package school.sptech.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import formatarDataDatePicker
import getColorTextEstoque
import kotlinx.coroutines.launch
import school.sptech.R
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.PretoOpacidade25
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Verde
import school.sptech.ui.theme.VerdeOpacidade15
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.VermelhoOpacidade15
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.viewModel.EnderecoViewModel
import school.sptech.ui.viewModel.ReporProdutoViewModel

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

@Composable
fun ProductModal(
    title: String,
    buttonColor: Color,
    buttonText: String,
    produto: String,
    quantidadeEstoque: Int,
    onDateSelected: (String?) -> Unit = {},
    onClickAdicionarData: () -> Unit = {},
    onQuantidadeChanged: (Int?) -> Unit = {},
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    viewModel: ReporProdutoViewModel = ReporProdutoViewModel(),
    datesFromBackend: List<String>,
    isRepor: Boolean = false
) {
    var data by remember { mutableStateOf("") }
//    viewModel.setQuantidadeInicial(0)

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
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

                Spacer(modifier = Modifier.height(8.dp))

                if (isRepor) {
                    Row(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(8.dp)) {
//                        LabelInput(label = stringResource(R.string.possui_validade))
//                        Checkbox(
//                            modifier = Modifier.size(24.dp),
//                            checked = produto.? : false,
//                            onCheckedChange = {
//                                viewModel.validade = viewModel.validade.copy(enabledValidade = it)
//                            },
//                            colors = CheckboxDefaults.colors(
//                                checkedColor = Preto,
//                                uncheckedColor = Cinza,
//                                checkmarkColor = Branco,
//                                disabledCheckedColor = CinzaOpacidade35,
//                                disabledUncheckedColor = CinzaOpacidade7,
//                                disabledIndeterminateColor = CinzaOpacidade7
//                            )
//                        )
                    }
                }

//                if (datesFromBackend.isNotEmpty() && datesFromBackend[0].isNotEmpty()) {
                SelectableDatesRow(
                    dates = datesFromBackend,
                    onDateSelected = {
                        data = it
                        onDateSelected(it)
                    },
                    onClickAdicionarData = onClickAdicionarData,
                    isRepor = isRepor,
                    qtdEstoqueData = viewModel.quantidadeEstoqueData.value
//                        quantidadeEstoque = viewModel.getQuantidadeMaxima()
                )
//                }

                Spacer(modifier = Modifier.height(12.dp))
                LabelInput(label = "Quantidade")
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        viewModel.diminuirQuantidade()
                        onQuantidadeChanged(viewModel.quantidade.value)
                    }

                    ) {
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
                        onClick = {
                            viewModel.aumentarQuantidade()
                            onQuantidadeChanged(viewModel.quantidade.value)
                        },
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
                        color = getColorTextEstoque(quantidadeEstoque)
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
    onDateSelected: (String?) -> Unit = {},
    onQuantidadeChanged: (Int?) -> Unit = {},
    onClickAdicionarData: () -> Unit = {},
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
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        onClickAdicionarData = onClickAdicionarData,
        onDateSelected = onDateSelected,
        onQuantidadeChanged = onQuantidadeChanged,
        viewModel = viewModel,
        datesFromBackend = datesFromBackend,
        isRepor = true
    )
}

@Composable
fun RetirarProductModal(
    produto: String,
    quantidadeEstoque: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    onDateSelected: (String?) -> Unit = {},
    onQuantidadeChanged: (Int?) -> Unit = {},
    viewModel: ReporProdutoViewModel = ReporProdutoViewModel(),
    datesFromBackend: List<String>
) {
    ProductModal(
        title = "Retirar Produto",
        buttonColor = Vermelho,
        buttonText = stringResource(id = R.string.retirar),
        produto = produto,
        quantidadeEstoque = quantidadeEstoque,
        onDateSelected = onDateSelected,
        onQuantidadeChanged = onQuantidadeChanged,
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        viewModel = viewModel,
        datesFromBackend = datesFromBackend
    )
}

@Composable
fun ModalConfirmarSair(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            ButtonBackground(titulo = "Confirmar", cor = RoxoNubank, onClick = onConfirm)
        },
        modifier = Modifier,
        dismissButton = {
            ButtonCancelar(onClick = onDismiss)
        },
        title = {
            Row(
                modifier =  Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                TituloLarge(titulo = "Sair da Conta")
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
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Deseja realmente sair da conta?",
                    fontFamily = fontFamilyPoppins,
                    color = Preto,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = letterSpacingPrincipal
                )
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = Branco,
        titleContentColor = Preto,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    )
}

@Composable
fun AlertError(msg: String) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .padding(bottom = 64.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExtendedFloatingActionButton(
            modifier = Modifier
//                .border(
//                    width = 1.dp,
//                    color = VermelhoOpacidade15,
//                    shape = RoundedCornerShape(16.dp)
//                )
                .fillMaxWidth(0.86f),
            text = {
                Text(
                    text = if (msg.isEmpty()) "Ops! Houve um erro inesperado. Tente novamente mais tarde." else msg,
                    color = Vermelho,
                    fontFamily = fontFamilyPoppins,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = letterSpacingPrincipal,
                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 2.dp)
                )
            },
            contentColor = Vermelho,
            containerColor = Color(0xFFF1C5CF),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 24.dp,
                pressedElevation = 24.dp,
                hoveredElevation = 24.dp,
                focusedElevation = 24.dp
            ),
            icon = {
                Icon(
                    painter = painterResource(id = R.mipmap.orangealert),
                    contentDescription = "",
                    modifier = Modifier.size(18.dp)
                )
            },
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Erro!!! $msg",
                        duration = SnackbarDuration.Short
                    )
                }
            },
        )
    }
}

@Composable
fun AlertSuccess(msg: String, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .padding(bottom = 64.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ExtendedFloatingActionButton(
            shape = RoundedCornerShape(16.dp),
            contentColor = Verde,
            containerColor = Color(0xFFD8F1D2),
            modifier = Modifier
//                .border(
//                    width = 1.dp,
//                    color = VerdeOpacidade15,
//                    shape = RoundedCornerShape(16.dp)
//                )
                .fillMaxWidth(0.86f),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 24.dp,
                pressedElevation = 24.dp,
                hoveredElevation = 24.dp,
                focusedElevation = 24.dp
            ),
            text = {
                Text(
                    text = msg,
                    color = Verde,
                    fontFamily = fontFamilyPoppins,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = letterSpacingPrincipal
                )
            },
            icon = { Icon(Icons.Filled.Check, contentDescription = "") },
            onClick = onClick
        )
    }
}

@Composable
fun ModalEditarEndereco(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    enderecoViewModel: EnderecoViewModel
) {
    val endereco = enderecoViewModel.endereco

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            ButtonCancelar(onClick = onDismiss)
        },
        confirmButton = {
            ButtonBackground(titulo = "Salvar", cor = RoxoNubank, onClick = onConfirm)
        },
        containerColor = Branco,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        title = {
            Row(modifier =  Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                TituloLarge(titulo = stringResource(id = R.string.editarEndereco))
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
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // CEP
                FormFieldWithLabel(
                    isSmallInput = true,
                    value = endereco.cep ?: "",  // Valor alterado
                    onValueChange = {
                        enderecoViewModel.endereco = enderecoViewModel.endereco.copy(cep = it)

                        if (it.length >= 9) {
                            enderecoViewModel.buscarEnderecoPorCep()
                        }
                    },
                    label = stringResource(id = R.string.cep)
                )

                FormFieldWithLabel(
                    isSmallInput = true,
                    value = endereco.logradouro ?: "",  // Valor alterado
                    onValueChange = {
                        enderecoViewModel.endereco =
                            enderecoViewModel.endereco.copy(logradouro = it)
                    },
                    label = stringResource(id = R.string.logradouro)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(0.3f)) {
                        FormFieldWithLabel(
                            isSmallInput = true,
                            value = endereco.numero.toString() ?: "",  // Valor alterado
                            onValueChange = {
                                enderecoViewModel.endereco =
                                    enderecoViewModel.endereco.copy(numero = it)
                            },
                            isNumericInput = true,
                            label = stringResource(id = R.string.numero)
                        )
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    Column(modifier = Modifier.weight(0.5f)) {
                        FormFieldWithLabel(
                            isSmallInput = true,
                            value = endereco.complemento ?: "",  // Valor alterado
                            onValueChange = {
                                enderecoViewModel.endereco =
                                    enderecoViewModel.endereco.copy(complemento = it)
                            },
                            label = stringResource(id = R.string.complemento)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(0.7f)) {
                        FormFieldWithLabel(
                            isSmallInput = true,
                            value = endereco.localidade ?: "",  // Valor alterado
                            onValueChange = {
                                enderecoViewModel.endereco =
                                    enderecoViewModel.endereco.copy(localidade = it)
                            },
                            isNumericInput = true,
                            label = stringResource(id = R.string.cidade)
                        )
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    Column(modifier = Modifier.weight(0.4f)) {
                        FormFieldWithLabel(
                            isSmallInput = true,
                            value = endereco.uf ?: "",  // Valor alterado
                            onValueChange = {
                                enderecoViewModel.endereco =
                                    enderecoViewModel.endereco.copy(uf = it)
                            },
                            label = stringResource(id = R.string.uf)
                        )
                    }
                }
            }
        }
    )
}
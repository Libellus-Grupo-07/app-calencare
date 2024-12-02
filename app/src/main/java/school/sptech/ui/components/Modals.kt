package school.sptech.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.substring
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import school.sptech.ui.theme.CinzaOpacidade7
import school.sptech.ui.theme.RoxoNubankOpacidade15
import school.sptech.ui.theme.RoxoNubankOpacidade7


@Composable
fun CustomMonthYearPickerDialog(
    mesSelecionado: Int,
    anoSelecionado: Int,
    onDismissRequest: () -> Unit,
    onConfirm: (Int, Int) -> Unit
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
    val anos = (2001..2100).toList()

    AlertDialog(
        modifier = Modifier
            .fillMaxWidth(),
        onDismissRequest = onDismissRequest,
        title = {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                TituloLarge(titulo = "Selecione o Mês e Ano")
//                IconButton(
//                    modifier = Modifier.size(24.dp),
//                    onClick = onDismissRequest
//                )
//                {
//                    Icon(Icons.Filled.Close, contentDescription = "Fechar", tint = Preto)
//                }
//            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val index = anos.indexOf(ano)
                    if (index > 0) {
                        ano = anos[index - 1]
                    } else {
                        ano = anos.last()
                    }
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Escolher mês anterior",
                        tint = Cinza,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = ano.toString(),
                    fontFamily = fontFamilyPoppins,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = letterSpacingPrincipal,
                    fontSize = 16.sp,
                    color = Cinza
                )

                IconButton(onClick = {
                    val index = anos.indexOf(ano)
                    if (index < anos.size - 1) {
                        ano = anos[index + 1]
                    } else {
                        ano = anos.first()
                    }
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Escolher próximo mês",
                        tint = Cinza
                    )
                }
            }
        },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(meses.size) {
                    TextButton(
                        contentPadding = PaddingValues(vertical = 20.dp),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = if (it == mes-1) RoxoNubankOpacidade7 else Color.Transparent,
                            contentColor = if (it == mes-1) RoxoNubank else Cinza
                        ),
                        onClick = { mes = it + 1 }
                    ) {
                        TextoButtonLarge(texto = meses[it].substring(0, 3))
                    }
                }
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
fun DatePickerModal(
    dateSelected: Long,
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit
) {
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
    nivelEstoque: String,
    quantidadeEstoque: Int,
    quantidadeEstoqueData: Int? = null,
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
    var quantidadeError by remember { mutableStateOf(false) }  // Estado para gerenciar o erro de quantidade
    var dataError by remember { mutableStateOf(false) }  // Estado para gerenciar o erro de data


    // Função para validar a quantidade
    fun isQuantidadeValida(): Boolean {
        return viewModel.quantidade.value > 0
    }

    // Função para validar a data
    fun isDataValida(): Boolean {
        return data.isNotEmpty()
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
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
                    readOnly = true,
                    isMediumInput = true
                )

                SelectableDatesRow(
                    dates = datesFromBackend,
                    onDateSelected = {
                        if (it != data) {
                            data = it
                            onDateSelected(it)
                            dataError = false
                        }
                    },
                    onClickAdicionarData = onClickAdicionarData,
                    isRepor = isRepor,
                    qtdEstoqueData = quantidadeEstoqueData ?: viewModel.quantidadeEstoqueData.value,
                    dataError = dataError,
                    isMedium = true
                )

                Spacer(modifier = Modifier.height(12.dp))
                LabelInput(label = "Quantidade", isMediumInput = true)
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        viewModel.diminuirQuantidade()
                        onQuantidadeChanged(viewModel.quantidade.value)
                    }) {
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
                            if (viewModel.quantidade.value
                                < if (!isRepor) quantidadeEstoqueData!!
                                else viewModel.quantidade.value + 1
                                ) {

                                viewModel.aumentarQuantidade()
                                onQuantidadeChanged(viewModel.quantidade.value)
                            }
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

                // Exibir mensagem de erro se a quantidade for inválida
                if (!isQuantidadeValida() && data.isNotEmpty()) {
                    Text(
                        text = "A quantidade deve ser maior que 0.",
                        color = Vermelho,
                        letterSpacing = letterSpacingPrincipal,
                        fontFamily = fontFamilyPoppins,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row() {
                    LabelInput(label = "Total em Estoque: ", isMediumInput = true)
                    LabelInput(
                        label = "$quantidadeEstoque produtos",
                        color = getColorTextEstoque(quantidadeEstoque, nivelEstoque )
                    )
                }
            }
        },
        confirmButton = {
            ButtonBackground(
                titulo = buttonText,
                cor = buttonColor,
                onClick = {
                    if (isQuantidadeValida() && isDataValida()) {
                        onConfirm(viewModel.quantidade.value)
                    } else {
                        dataError = true  // Indica erro de validação
                        quantidadeError = true  // Indica erro de validação
                    }
                },
                enabled = isQuantidadeValida()  // Desabilita o botão se a quantidade for inválida
            )
        },
        dismissButton = {
            ButtonCancelar(onClick = onDismiss)
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preview() {
    ProductModal(
        title = "Despesa",
        buttonColor = RoxoNubank,
        buttonText = "repor",
        produto = "",
        quantidadeEstoque = 10,
        onDismiss = { /*TODO*/ },
        onConfirm = { /*TODO*/ },
        datesFromBackend = listOf("1999/11/02"),
        nivelEstoque = "Estoque baixo"
    )
}


@Composable
fun ReporProductModal(
    produto: String,
    nivelEstoque: String,
    quantidadeEstoque: Int,
    quantidadeEstoqueData: Int? = null,
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
        quantidadeEstoqueData = quantidadeEstoqueData,
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        onClickAdicionarData = onClickAdicionarData,
        onDateSelected = onDateSelected,
        onQuantidadeChanged = onQuantidadeChanged,
        viewModel = viewModel,
        datesFromBackend = datesFromBackend,
        isRepor = true,
        nivelEstoque = nivelEstoque
    )
}

@Composable
fun RetirarProductModal(
    produto: String,
    nivelEstoque: String,
    quantidadeEstoque: Int,
    quantidadeEstoqueData: Int?,
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
        quantidadeEstoqueData = quantidadeEstoqueData,
        onDateSelected = onDateSelected,
        onQuantidadeChanged = onQuantidadeChanged,
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        viewModel = viewModel,
        datesFromBackend = datesFromBackend,
        nivelEstoque = nivelEstoque
    )
}

@Composable
fun ModalConfirmar(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    titulo: String,
    texto: String,
    nomeItem: String
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
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                TituloLarge(titulo = titulo)
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
                val fontStyle = SpanStyle(
                    color = Cinza,
                    fontFamily = fontFamilyPoppins,
                    letterSpacing = letterSpacingPrincipal
                )

                Text(buildAnnotatedString {
                    withStyle(style = fontStyle) {
                        append(texto)
                    }
                    withStyle(style = fontStyle.copy(color = Preto, fontWeight = FontWeight.Bold)) {
                        append(nomeItem)
                    }
                    withStyle(style = fontStyle) {
                        append("?")
                    }
                })
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
fun ModalConfirmarExclusao(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    titulo: String,
    texto: String,
    nomeItem: String
) {
    ModalConfirmar(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        titulo = titulo,
        texto = texto,
        nomeItem = nomeItem
    )
}

@Composable
fun ModalConfirmarSair(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    ModalConfirmar(
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        titulo = "Sair",
        texto = "Deseja realmente sair de sua conta",
        nomeItem = ""
    )
}

@Composable
fun AlertError(msg: String, onClick: () -> Unit = {}) {
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
            // .fillMaxWidth(0.86f),
            , text = {
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
            onClick = onClick
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
            containerColor = Color(0xFFD8F1D2).copy(alpha = 0.96f),
//            containerColor = Color.Transparent,
            modifier = Modifier
//                .border(
//                    width = 1.dp,
//                    color = VerdeOpacidade15,
//                    shape = RoundedCornerShape(16.dp)
//                )
            //.fillMaxWidth(0.86f)
            ,
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
                    letterSpacing = letterSpacingPrincipal,
                    fontSize = 13.5.sp
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
    var endereco = enderecoViewModel.endereco
    var cepPreenchido by remember { mutableStateOf(endereco.cep?.isNotEmpty() ?: false) }
    var logradouroPreenchido by remember {
        mutableStateOf(
            endereco.logradouro?.isNotEmpty() ?: false
        )
    }
    var numeroPreenchido by remember {
        mutableStateOf(endereco.numero?.isNotEmpty() ?: false || endereco.numero.equals("0"))
    }
    var bairroPreenchido by remember { mutableStateOf(endereco.bairro?.isNotEmpty() ?: false) }
    var cidadePreenchida by remember { mutableStateOf(endereco.localidade?.isNotEmpty() ?: false) }
    var ufPreenchida by remember { mutableStateOf(endereco.uf?.isNotEmpty() ?: false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            ButtonCancelar(onClick = onDismiss)
        },
        confirmButton = {
            ButtonBackground(
                titulo = "Salvar",
                cor = RoxoNubank,
                onClick = {
                    if (enderecoViewModel.endereco.cep?.isNotEmpty() == true
                        && enderecoViewModel.endereco.logradouro?.isNotEmpty() == true
                        && enderecoViewModel.endereco.numero?.isNotEmpty() == true
                        && enderecoViewModel.endereco.localidade?.isNotEmpty() == true
                        && enderecoViewModel.endereco.uf?.isNotEmpty() == true
                    ) {
                        onConfirm()
                    }
                }
            )
        },
        containerColor = Branco,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        title = {
            Row(modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)) {
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
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // CEP
                FormFieldWithLabel(
                    isSmallInput = true,
                    value = endereco.cep ?: "",  // Valor alterado
                    onValueChange = {
                        enderecoViewModel.endereco = enderecoViewModel.endereco.copy(cep = it)

                        if (it.length >= 8) {
                            endereco = enderecoViewModel.getEnderecoViaCep()
                        }

                        cepPreenchido = it.isNotEmpty()
                    },
                    label = stringResource(id = R.string.cep),
                    isObrigatorio = true,
                    error = !cepPreenchido
                )

                FormFieldWithLabel(
                    isSmallInput = true,
                    value = endereco.logradouro ?: "",  // Valor alterado
                    onValueChange = {
                        enderecoViewModel.endereco =
                            enderecoViewModel.endereco.copy(logradouro = it)
                        logradouroPreenchido = it.isNotEmpty()
                    },
                    label = stringResource(id = R.string.logradouro),
                    isObrigatorio = true,
                    error = !logradouroPreenchido
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(0.35f)) {
                        FormFieldWithLabel(
                            isSmallInput = true,
                            value = endereco.numero.toString() ?: "",  // Valor alterado
                            onValueChange = {
                                enderecoViewModel.endereco =
                                    enderecoViewModel.endereco.copy(numero = it)
                                numeroPreenchido =
                                    it.isNotEmpty() || it.equals(
                                        "0"
                                    )
                            },
                            isNumericInput = true,
                            label = stringResource(id = R.string.numero),
                            isObrigatorio = true,
                            error = !numeroPreenchido
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
                            label = stringResource(id = R.string.complemento),
                            isObrigatorio = false
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
                                cidadePreenchida = it.isNotEmpty()
                            },
                            isNumericInput = true,
                            label = stringResource(id = R.string.cidade),
                            isObrigatorio = true,
                            error = !cidadePreenchida
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
                                ufPreenchida = endereco.uf?.isNotEmpty() ?: false
                            },
                            label = stringResource(id = R.string.uf),
                            isObrigatorio = true,
                            error = !ufPreenchida
                        )
                    }
                }
            }
        }
    )
}
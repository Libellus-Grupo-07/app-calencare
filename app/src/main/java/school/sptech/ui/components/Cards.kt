package school.sptech.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import formatarData
import formatarDataDatePicker
import formatarDecimal
import formatarValorMonetario
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import school.sptech.R
import school.sptech.data.model.Despesa
import school.sptech.data.model.Movimentacoes
import school.sptech.data.model.Movimentos
import school.sptech.data.model.Produto
import school.sptech.data.model.Validade
import school.sptech.ui.theme.Amarelo
import school.sptech.ui.theme.Azul
import school.sptech.ui.theme.AzulOpacidade15
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.CinzaOpacidade35
import school.sptech.ui.theme.Laranja
import school.sptech.ui.theme.LaranjaDourado
import school.sptech.ui.theme.LaranjaOpacidade15
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.PretoOpacidade15
import school.sptech.ui.theme.PretoOpacidade25
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Verde
import school.sptech.ui.theme.VerdeOpacidade15
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.VermelhoOpacidade15
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.theme.letterSpacingSecundaria
import school.sptech.ui.viewModel.ReporProdutoViewModel
import school.sptech.ui.viewModel.ValidadeViewModel
import transformarEmLocalDateTime
import java.time.LocalDate
import java.util.Locale

@Composable
fun CardKpi(titulo: String, valor: String, cor: String, modifier: Modifier = Modifier) {
    var corFundo: Color
    var corTexto: Color

    when (cor.uppercase()) {
        "VERMELHO" -> {
            corFundo = VermelhoOpacidade15
            corTexto = Vermelho
        }

        "LARANJA" -> {
            corFundo = LaranjaOpacidade15
            corTexto = Laranja
        }

        "AZUL" -> {
            corFundo = AzulOpacidade15
            corTexto = Azul
        }

        else -> {
            corFundo = VerdeOpacidade15;
            corTexto = Verde
        }
    }

    Box(modifier = modifier.clip(RoundedCornerShape(16.dp))) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(corFundo)
                .padding(horizontal = 16.dp, vertical = 21.dp),
            Arrangement.Start,
        ) {
            Column {
                LabelKpi(label = titulo)
                TextoKpi(
                    valor = valor,
                    cor = corTexto
                )
            }
        }
    }
}

@Composable
fun CardProduto(
    validadeViewModel: ValidadeViewModel = viewModel(),
    reporProdutoViewModel: ReporProdutoViewModel = viewModel(),
    produto: Produto,
    isTelaInicio: Boolean,
    onClickCardProduto: () -> Unit,
    modifier: Modifier = Modifier
) {
    var exibirModalRepor by remember { mutableStateOf(false) }
    var exibirModalRetirar by remember { mutableStateOf(false) } // Controle do modal de retirada
    var exibirModalAdicionarData by remember { mutableStateOf(false) } // Controle do modal de retirada
    var dateValue by remember { mutableStateOf(0L) }

    LaunchedEffect("estoque") {
        validadeViewModel.getValidades(produto.id!!)
        //validadeViewModel.getTotalEstoqueProduto(produto.id!!)
        //validadeViewModel.atualizarQtdEstoqueValidades()
    }

    produto.validades = validadeViewModel.listaValidades

    Row(
        modifier = modifier
            .border(
                width = 1.3.dp,
                brush = Brush.linearGradient(
                    colors = listOf(LaranjaDourado, RoxoNubank),
                    start = Offset.Zero,
                    end = Offset.Infinite,
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .background(Branco, shape = RoundedCornerShape(20.dp))
            .height(200.dp)
            .clickable(
                onClick = onClickCardProduto

            )
    ) {
        Box(
            modifier = Modifier
                .padding(18.dp)
        ) {

            Column() {
                Text(
                    text = produto.nome ?: "",
                    fontSize = 12.5.sp,
                    lineHeight = 20.sp,
                    color = RoxoNubank,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = letterSpacingPrincipal,
                    fontFamily = fontFamilyPoppins,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxHeight(0.25f)
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = produto.categoriaProdutoNome ?: "",
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = letterSpacingPrincipal,
                    lineHeight = 15.sp,
                    color = Cinza,
                    fontFamily = fontFamilyPoppins
                )

                Spacer(modifier = Modifier.size(8.dp))

                ButtonEstoque(
                    qtdEstoque = produto.qntdTotalEstoque ?: 0,
                )

                if (isTelaInicio) {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = CircleShape,
                        onClick = {
                            validadeViewModel.deuErro = false
                            validadeViewModel.erro = ""
                            exibirModalRepor = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Branco,
                            containerColor = RoxoNubank,
                        )
                    ) {
                        Icon(
                            Icons.Rounded.Add,
                            contentDescription = "Ícone Adicionar",
                            modifier = Modifier.size(15.dp)
                        )

                        Spacer(modifier = Modifier.size(4.dp))

                        Text(
                            text = stringResource(id = R.string.reporEstoque),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            fontFamily = fontFamilyPoppins,
                            letterSpacing = letterSpacingPrincipal
                        )
                    }
                } else {
                    val buttonRetirarEnabled = produto.qntdTotalEstoque ?: 0 > 0

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        Arrangement.spacedBy(4.dp),
                        Alignment.CenterVertically,
                    ) {
                        TextButton(
                            modifier = Modifier
                                .weight(0.5f)
                                .height(36.dp),
                            shape = CircleShape,
                            onClick = {
                                validadeViewModel.deuErro = false
                                validadeViewModel.erro = ""
                                exibirModalRetirar = true
                            }, // Atualiza o estado para abrir o modal de retirada
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Cinza,
                                containerColor = Branco,
                                disabledContentColor = CinzaOpacidade35,
                                disabledContainerColor = Branco
                            ),
                            enabled = buttonRetirarEnabled,
                            border = BorderStroke(
                                1.5.dp,
                                if (buttonRetirarEnabled) Cinza else CinzaOpacidade35
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.retirar),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                fontFamily = fontFamilyPoppins,
                                letterSpacing = letterSpacingPrincipal
                            )
                        }

                        TextButton(
                            modifier = Modifier
                                .weight(0.5f)
                                .height(36.dp),
                            shape = CircleShape,
                            onClick = {
                                validadeViewModel.deuErro = false
                                validadeViewModel.erro = ""
                                exibirModalRepor = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Branco,
                                containerColor = RoxoNubank,
                                disabledContentColor = CinzaOpacidade35,
                                disabledContainerColor = Branco
                            ),
                        ) {
                            Text(
                                text = stringResource(id = R.string.repor),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                fontFamily = fontFamilyPoppins,
                                letterSpacing = letterSpacingPrincipal
                            )
                        }
                    }
                }
            }

            // Exibe o modal de reposição de produto
            if (exibirModalRepor) {
                validadeViewModel.deuErro = false
                validadeViewModel.erro = ""

                LaunchedEffect(Unit) {
                    validadeViewModel.getValidades(produto.id!!)
                }

                ReporProductModal(
                    onDismiss = {
                        exibirModalRepor = false
                        reporProdutoViewModel.quantidadeEstoqueData.value = 0
                        reporProdutoViewModel.setQuantidadeInicial(0)
                        reporProdutoViewModel.setQuantidadeMaxima(0)
                        validadeViewModel.quantidadeEstoqueValidade = 0
                    },
                    produto = produto.nome ?: "",
                    quantidadeEstoque = produto.qntdTotalEstoque ?: 0,
                    onDateSelected = {
                        reporProdutoViewModel.quantidadeEstoqueData.value = 0
                        validadeViewModel.validade = Validade()
                        val validade = produto.validades?.find { validadeAtual ->
                            it!!.equals((validadeAtual.dtValidade ?: ""))
                                    && validadeAtual.produtoId == produto.id
                        }

                        validadeViewModel.validade = validade!!
                        reporProdutoViewModel.quantidadeEstoqueData.value =
                            validadeViewModel.getQuantidadeEstoqueDaValidade()
                        reporProdutoViewModel.setQuantidadeMaxima(null)
                    },
                    onQuantidadeChanged = {
                        validadeViewModel.movimentacaoValidade =
                            validadeViewModel.movimentacaoValidade.copy(quantidade = it)
                    },
                    viewModel = reporProdutoViewModel,
                    onClickAdicionarData = {
                        exibirModalAdicionarData = true
                    },
                    onConfirm = {
                        validadeViewModel.reporEstoque()

                        if (!validadeViewModel.deuErro) {
                            produto.qntdTotalEstoque = produto.qntdTotalEstoque?.plus(
                                validadeViewModel.movimentacaoValidade.quantidade ?: 0
                            )

                            //validadeViewModel.atualizarQtdEstoqueValidades()
                            reporProdutoViewModel.setQuantidadeInicial(0)
                            reporProdutoViewModel.setQuantidadeMaxima(0)
                            reporProdutoViewModel.quantidadeEstoqueData.value = 0
                            validadeViewModel.quantidadeEstoqueValidade = 0

                            exibirModalRepor = false
                        }
                    },
                    datesFromBackend = produto.validades?.map { it.dtValidade ?: "" } ?: listOf()
                )
            }

            // Exibe o modal de retirada de produto
            if (exibirModalRetirar) {
                LaunchedEffect(Unit) {
                    validadeViewModel.getValidades(produto.id!!)
                }

                RetirarProductModal(
                    produto = produto.nome ?: "",
                    quantidadeEstoque = produto.qntdTotalEstoque ?: 0,
                    onDismiss = {
                        exibirModalRetirar = false
                        reporProdutoViewModel.setQuantidadeInicial(0)
                        reporProdutoViewModel.setQuantidadeMaxima(0)
                        reporProdutoViewModel.quantidadeEstoqueData.value = 0
                        validadeViewModel.quantidadeEstoqueValidade = 0
                    },
                    onConfirm = {
                        validadeViewModel.retirarEstoque()

                        if (!validadeViewModel.deuErro && validadeViewModel.erro.isNotEmpty()) {
                            produto.qntdTotalEstoque = produto.qntdTotalEstoque?.minus(
                                validadeViewModel.movimentacaoValidade.quantidade ?: 0
                            )
                            reporProdutoViewModel.setQuantidadeInicial(0)
                            reporProdutoViewModel.setQuantidadeMaxima(0)
                            reporProdutoViewModel.quantidadeEstoqueData.value = 0
                            validadeViewModel.quantidadeEstoqueValidade = 0
                            exibirModalRetirar = false
                        }
                    },
                    viewModel = reporProdutoViewModel,
                    onDateSelected = {
                        reporProdutoViewModel.quantidadeEstoqueData.value = 0

                        val validade = produto.validades?.find { validadeAtual ->
                            it!!.equals(validadeAtual.dtValidade ?: "")
                        }

                        validadeViewModel.validade = validade!!
                        validadeViewModel.getQuantidadeEstoquePorValidade()

                        reporProdutoViewModel.quantidadeEstoqueData.value =
                            validadeViewModel.quantidadeEstoqueValidade
                        reporProdutoViewModel.setQuantidadeMaxima(validadeViewModel.quantidadeEstoqueValidade)
                        reporProdutoViewModel.setQuantidadeInicial(0)
                    },
                    onQuantidadeChanged = {
                        validadeViewModel.movimentacaoValidade =
                            validadeViewModel.movimentacaoValidade.copy(quantidade = it)
                    },
                    datesFromBackend = produto.validades?.map { it.dtValidade ?: "" } ?: listOf()
                )
            }

            if (exibirModalAdicionarData) {
                exibirModalRepor = false

                DatePickerModal(
                    dateSelected = dateValue,
                    onDismiss = {
                        exibirModalAdicionarData = false
                        exibirModalRepor = true
                    },
                    onDateSelected = { date ->
                        validadeViewModel.validade =
                            validadeViewModel.validade.copy(idProduto = produto.id ?: 0)
                        validadeViewModel.validade = validadeViewModel.validade.copy(
                            dtValidade = transformarEmLocalDateTime(
                                formatarDataDatePicker(
                                    inputFormat = true,
                                    data = date
                                )
                            ).toString()
                        )

                        validadeViewModel.adicionarValidade()
                        produto.validades = MutableList(produto.validades?.size ?: 0) {
                            validadeViewModel.getValidades(produto.id ?: 0)?.get(it) ?: Validade()
                        }

                        exibirModalAdicionarData = false
                        exibirModalRepor = true
                    }
                )
            }

            if (validadeViewModel.deuErro) {
                exibirModalRepor = false
                exibirModalRetirar = false
                exibirModalAdicionarData = false
            }

            if (exibirModalRepor || exibirModalRetirar) {
                if (!validadeViewModel.deuErro && validadeViewModel.erro.isNotEmpty()) {
                    exibirModalRepor = false
                    exibirModalRetirar = false
                    exibirModalAdicionarData = false
                }
            }
        }
    }
}

@Composable
fun CardMovimentos(
    movimentos: Movimentacoes,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .shadow(
                4.dp,
                RoundedCornerShape(20.dp),
                ambientColor = Color.Transparent,
                spotColor = PretoOpacidade25
            ),
        colors = CardDefaults.cardColors(
            containerColor = Branco
        ),
    ) {
        val iconId = when (movimentos.descricao) {
            "Despesas" -> R.mipmap.icone_dinheiro_despesa
            "Agendamentos" -> R.mipmap.agendamentos
            else -> R.mipmap.comissoes
        }

        val corIcone = when (movimentos.descricao) {
            "Despesas" -> VermelhoOpacidade15
            "Agendamentos" -> AzulOpacidade15
            else -> LaranjaOpacidade15
        }

        val corTexto = when (movimentos.descricao) {
            "Despesas" -> Vermelho
            "Agendamentos" -> Azul
            else -> Laranja
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Ícone de ${movimentos.descricao ?: "Movimentos"}",
                tint = corTexto,
                modifier = Modifier.size(52.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row {
                    Text(
                        text = formatarData(movimentos.data ?: "") ?: "",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = fontFamilyPoppins,
                        letterSpacing = letterSpacingPrincipal,
                        color = Cinza
                    )
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    val isDespesa by remember { mutableStateOf(movimentos.descricao == "Despesas") }

                    Text(
                        text = movimentos.descricao ?: "",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fontFamilyPoppins,
                        letterSpacing = letterSpacingPrincipal,
                        color = Preto
                    )

                    Text(
                        text = stringResource(
                            id = R.string.valorMovimentos,
                            if (isDespesa) "-" else "+",
                            formatarDecimal(movimentos.total?.toFloat() ?: 0.0)
                        ),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = fontFamilyPoppins,
                        letterSpacing = letterSpacingPrincipal,
                        color = corTexto
                    )
                }
            }

            Icon(
                Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "Ver detalhes",
                tint = Cinza
            )
        }
    }
}

@Composable
fun CardDespesa(despesa: Despesa) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .border(0.dp, PretoOpacidade15, RoundedCornerShape(20.dp))
            .shadow(
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color.Transparent,
                spotColor = PretoOpacidade25,
                elevation = 4.dp,
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Branco)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 18.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.icone_dinheiro_despesa),
                    contentDescription = "Imagem Despesa",
                    modifier = Modifier
                        .size(75.dp)
                        .padding(8.dp)
                )
                Column(
                    modifier = Modifier.padding(start = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = despesa.categoriaDespesaNome?.uppercase() ?: "",
                            fontSize = 12.5.sp,
                            color = Cinza,
                            fontWeight = FontWeight.Bold,
                        )
                        TextButton(
                            modifier = Modifier.height(28.dp),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = VerdeOpacidade15,
                                contentColor = Verde
                            ),
                            contentPadding = PaddingValues(
                                horizontal = 12.dp,
                            ),
                            onClick = { /*TODO*/ }
                        ) {
                            Text(
                                text = "Pago em ${despesa.dtPagamento?.let { formatarData(it) }}",
                                //                text = despesa.dtPagamento ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                fontFamily = fontFamilyPoppins,
                                letterSpacing = letterSpacingSecundaria
                            )
                        }
                    }

                    Text(
                        text = despesa.nome?.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }
                            ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Preto,
                    )

                    Text(
                        text = despesa.valor?.let { formatarValorMonetario(it.toFloat()) } ?: "",
                        fontSize = 16.sp,
                        color = Vermelho,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = letterSpacingPrincipal
                    )
                }
            }
        }
    }
}

@Composable
fun CardNotificacoes(dtHora: String, nomeProduto: String, qntdProduto: Int) {
    var cor = Color.Black
    var img = R.mipmap.orangealert
    var textoAlerta = ""

    if (qntdProduto in 11..20) {
        cor = Amarelo
        img = R.mipmap.yellowalert
        textoAlerta = stringResource(R.string.estoqueBaixo)
    } else if (qntdProduto in 1..10) {
        cor = Laranja
        img = R.mipmap.orangealert
        textoAlerta = stringResource(R.string.quaseSemEstoque)
    } else if (qntdProduto == 0) {
        cor = Vermelho
        img = R.mipmap.redalert
        textoAlerta = stringResource(R.string.semEstoque)
    }

    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = dtHora,
                    fontFamily = fontFamilyPoppins,
                    letterSpacing = letterSpacingPrincipal,
                    color = Color(88, 88, 88, 255),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Image(
                        painter = painterResource(id = img),
                        contentDescription = "Ícone de indicação de alerta",
                        Modifier.size(16.dp)
                    )
                }

                Column {
                    Text(
                        text = textoAlerta,
                        color = cor,
                        letterSpacing = letterSpacingPrincipal,
                        fontFamily = fontFamilyPoppins,
                        style = TextStyle(
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
        }

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 25.dp, start = 25.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MultiStyleText(
                    stringResource(R.string.oProduto), Preto,
                    nomeProduto, RoxoNubank,
                    stringResource(R.string.situacaoEstoque), Preto,
                    stringResource(R.string.qtdProdutoUnidade, qntdProduto), RoxoNubank
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                color = Color.LightGray
            )
        }
    }
}

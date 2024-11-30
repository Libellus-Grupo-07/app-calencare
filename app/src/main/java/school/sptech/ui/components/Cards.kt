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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import formatarData
import formatarDataDatePicker
import formatarDecimal
import formatarValorMonetario
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.R
import school.sptech.data.model.Agendamento
import school.sptech.data.model.Despesa
import school.sptech.data.model.Movimentacoes
import school.sptech.data.model.Produto
import school.sptech.data.model.Validade
import school.sptech.ui.theme.Amarelo
import school.sptech.ui.theme.Azul
import school.sptech.ui.theme.AzulClaro
import school.sptech.ui.theme.AzulOpacidade15
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.CinzaOpacidade35
import school.sptech.ui.theme.Laranja
import school.sptech.ui.theme.LaranjaDourado
import school.sptech.ui.theme.LaranjaEscuro
import school.sptech.ui.theme.LaranjaOpacidade15
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.PretoOpacidade15
import school.sptech.ui.theme.PretoOpacidade25
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Verde
import school.sptech.ui.theme.VerdeOpacidade15
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.VermelhoLaranja
import school.sptech.ui.theme.VermelhoOpacidade15
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.theme.letterSpacingSecundaria
import school.sptech.ui.viewModel.ReporProdutoViewModel
import school.sptech.ui.viewModel.ValidadeViewModel
import transformarEmLocalDateTime
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
                reporProdutoViewModel.quantidadeEstoqueData.value =
                    validadeViewModel.quantidadeEstoqueValidade

                LaunchedEffect(Unit) {
                    validadeViewModel.getValidades(produto.id!!)
                }

                ReporProductModal(
                    onDismiss = {
                        exibirModalRepor = false
                        reporProdutoViewModel.quantidadeEstoqueData.value = 0
                        reporProdutoViewModel.setQuantidadeInicial(0)
                        reporProdutoViewModel.setValorQuantidadeMaxima(0)
                        validadeViewModel.quantidadeEstoqueValidade = 0
                    },
                    produto = produto.nome ?: "",
                    quantidadeEstoque = produto.qntdTotalEstoque ?: 0,
                    quantidadeEstoqueData = validadeViewModel.quantidadeEstoqueValidade,
                    onDateSelected = {
                        reporProdutoViewModel.quantidadeEstoqueData.value = 0
                        validadeViewModel.validade = Validade()
                        val validade = validadeViewModel.getListaValidades().find { validadeAtual ->
                            it!!.equals((validadeAtual.dtValidade ?: ""))
                                    && validadeAtual.produtoId == produto.id
                        }

                        validadeViewModel.validade = validade!!

                        validadeViewModel.getQuantidadeEstoquePorValidade(validade.id!!)
                        reporProdutoViewModel.setValorQuantidadeMaxima(null)
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
                            reporProdutoViewModel.setValorQuantidadeMaxima(null)
                            reporProdutoViewModel.quantidadeEstoqueData.value = 0
                            validadeViewModel.quantidadeEstoqueValidade = 0

                            exibirModalRepor = false
                        }
                    },
                    datesFromBackend = validadeViewModel.getListaValidades()
                        .map { it.dtValidade ?: "" }
                )
            }

            // Exibe o modal de retirada de produto
            if (exibirModalRetirar) {
                LaunchedEffect(Unit) {
                    if (validadeViewModel.getListaValidades().isEmpty()) {
                        validadeViewModel.getValidades(produto.id!!)
                    }
                }

                RetirarProductModal(
                    produto = produto.nome ?: "",
                    quantidadeEstoqueData = validadeViewModel.quantidadeEstoqueValidade,
                    quantidadeEstoque = produto.qntdTotalEstoque ?: 0,
                    onDismiss = {
                        exibirModalRetirar = false
                        reporProdutoViewModel.setQuantidadeInicial(0)
                        reporProdutoViewModel.setValorQuantidadeMaxima(0)
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
                            reporProdutoViewModel.setValorQuantidadeMaxima(null)
                            reporProdutoViewModel.quantidadeEstoqueData.value = 0
                            validadeViewModel.quantidadeEstoqueValidade = 0
                            exibirModalRetirar = false
                        }
                    },
                    viewModel = reporProdutoViewModel,
                    onDateSelected = {
                        reporProdutoViewModel.quantidadeEstoqueData.value = 0

                        val validade = validadeViewModel.getListaValidades().find { validadeAtual ->
                            it!!.equals((validadeAtual.dtValidade ?: ""))
                                    && validadeAtual.produtoId == produto.id
                        }

                        validadeViewModel.validade = validade!!

                        validadeViewModel.getQuantidadeEstoquePorValidade(validade.id!!)


                        reporProdutoViewModel.setValorQuantidadeMaxima(null)
                        reporProdutoViewModel.setQuantidadeInicial(0)
                    },
                    onQuantidadeChanged = {
                        validadeViewModel.movimentacaoValidade =
                            validadeViewModel.movimentacaoValidade.copy(quantidade = it)
                    },
                    datesFromBackend = validadeViewModel.getListaValidades()
                        .map { it.dtValidade ?: "" } ?: listOf()
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
fun CardMovimentacoes(
    movimentacoes: Movimentacoes,
    onClick: () -> Unit,
    isLargeCard: Boolean = false,
    isClickableCard: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = if (isLargeCard) 0.dp else 4.dp)
            .shadow(
                4.dp,
                RoundedCornerShape(20.dp),
                ambientColor = Color.Transparent,
                spotColor = PretoOpacidade25
            )
            .clickable(enabled = isClickableCard) {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Branco
        ),
    ) {
        val iconId = when (movimentacoes.descricao) {
            "Despesas" -> R.mipmap.icone_dinheiro_despesa
            "Agendamentos" -> R.mipmap.agendamentos
            else -> R.mipmap.comissoes
        }

        val corIcone = when (movimentacoes.descricao) {
            "Despesas" -> VermelhoOpacidade15
            "Agendamentos" -> AzulOpacidade15
            else -> LaranjaOpacidade15
        }

        val corTexto = when (movimentacoes.descricao) {
            "Despesas" -> Vermelho
            "Agendamentos" -> Azul
            else -> Laranja
        }

        val isDespesa by remember { mutableStateOf(movimentacoes.descricao == "Despesas") }
        val valorFormatado = stringResource(
            id = R.string.valorMovimentos,
            if (isDespesa) "-" else "+",
            formatarDecimal(movimentacoes.total?.toFloat() ?: 0.0)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Ícone de ${movimentacoes.descricao ?: "Movimentos"}",
                tint = corTexto,
                modifier = Modifier.size(if (isLargeCard) 58.dp else 52.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(if (isLargeCard) 0.94f else 0.8f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row {
                    Text(
                        text = formatarData(movimentacoes.data ?: "") ?: "",
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

                    Text(
                        text = movimentacoes.descricao ?: "",
                        fontSize = if (isLargeCard) 14.sp else 13.sp,
                        fontWeight = if (isLargeCard) FontWeight.Bold else FontWeight.SemiBold,
                        fontFamily = fontFamilyPoppins,
                        letterSpacing = letterSpacingPrincipal,
                        color = Preto
                    )

                    TextoValorColorido(
                        texto = valorFormatado,
                        cor = corTexto
                    )

                }
            }

            if (isClickableCard) {
                Icon(
                    Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = "Ver detalhes",
                    tint = Cinza,
                    modifier = Modifier.clickable(enabled = isClickableCard) {
                        onClick()
                    }
                )
            }
        }
    }
}

@Composable
fun CardDescricaoMovimentacao(
    valor: Double,
    titulo: String,
    data: String,
    profissional: String = "",
    cliente: String = "",
    tipoCard: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
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
        val iconId = when (tipoCard) {
            "Despesas" -> R.mipmap.despesa_extrato
            "Agendamentos" -> R.mipmap.agendamento_ticket
            else -> R.mipmap.comissao_pessoa
        }

        val corTexto = when (tipoCard) {
            "Despesas" -> VermelhoLaranja
            "Agendamentos" -> AzulClaro
            else -> LaranjaEscuro
        }

        val isAgendamento by remember { mutableStateOf(tipoCard == "Agendamentos") }
        val valorFormatado = stringResource(
            id = R.string.valorMovimentos,
            if (isAgendamento) "+" else "-",
            formatarDecimal(valor.toFloat())
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    top = 16.dp,
                    end = 20.dp,
                    bottom = if (isAgendamento) 6.dp else 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Ícone de $tipoCard",
                tint = corTexto,
                modifier = Modifier.size(54.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(0.92f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row {
                    Text(
                        text = data,
                        fontSize = 10.sp,
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

                    Text(
                        text = titulo,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamilyPoppins,
                        letterSpacing = letterSpacingPrincipal,
                        color = Preto
                    )

                    TextoValorColorido(
                        texto = valorFormatado,
                        cor = corTexto
                    )
                }
            }
        }

        if (isAgendamento) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    .drawBehind {
                        drawLine(
                            color = Cinza,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1f
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                val spanStyleCinza = SpanStyle(
                    color = Cinza,
                    fontFamily = fontFamilyPoppins,
                    letterSpacing = letterSpacingPrincipal,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Normal
                )

                val spanStylePreto = SpanStyle(
                    color = Preto,
                    fontFamily = fontFamilyPoppins,
                    letterSpacing = letterSpacingPrincipal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.5.sp,
                )

                Row(
                    modifier = modifier
                        .fillMaxWidth(0.95f)
                        .padding(top = 10.dp, bottom = 12.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    val primeiraLetraSobrenomeProfissional =
                        profissional.substringAfter(" ").substring(0, 1).uppercase()
                    val nomeProfissional =
                        profissional.replaceAfter(" ", "$primeiraLetraSobrenomeProfissional.")
                    val primeiraLetraSobrenomeCliente =
                        cliente.substringAfter(" ").substring(0, 1).uppercase()
                    val nomeCliente = cliente.replaceAfter(" ", "$primeiraLetraSobrenomeCliente.")

                    Text(
                        buildAnnotatedString {
                            withStyle(
                                spanStyleCinza
                            ) {
                                append("Profissional: ")
                            }
                            withStyle(
                                spanStylePreto
                            ) {
                                append(nomeProfissional)
                            }
                        },
                    )

                    Text(
                        buildAnnotatedString {
                            withStyle(
                                spanStyleCinza
                            ) {
                                append("Cliente: ")
                            }
                            withStyle(
                                spanStylePreto
                            ) {
                                append(nomeCliente)
                            }
                        },
                    )
                }
            }
        }
    }
}


@Composable
fun CardDespesaMovimentacao(despesa: Despesa) {
    CardDescricaoMovimentacao(
        valor = despesa.valor?.toDoubleOrNull() ?: 0.0,
        titulo = despesa.nome ?: "",
        data = "Pago em ${despesa.dtPagamento?.let { formatarData(it) }}",
        tipoCard = "Despesas"
    )
}

@Composable
fun CardAgendamentoMovimentacao(agendamento: Agendamento) {
    val dia = agendamento.dia?.let { formatarData(it) } ?: ""
    val hrInicio = agendamento.horario?.substring(0, 5)
    val hrFim = agendamento.horarioFinalizacao?.substring(0, 5)

    CardDescricaoMovimentacao(
        valor = agendamento.preco ?: 0.0,
        titulo = agendamento.nomeServico ?: "",
        data = "$dia $hrInicio às $hrFim",
        profissional = agendamento.nomeFuncionario ?: "",
        cliente = agendamento.nomeCliente ?: "",
        tipoCard = "Agendamentos"
    )
}

@Composable
fun CardComissaoMovimentacao(comissao: Movimentacoes) {
    CardDescricaoMovimentacao(
        valor = comissao.total?.toDoubleOrNull() ?: 0.0,
        titulo = comissao.descricao ?: "",
        data = "Pago em ${comissao.data?.let { formatarData(it) }}",
        tipoCard = "Comissões"
    )
}

@Composable
fun CardDespesa(despesa: Despesa, onClickDespesa: () -> Unit) {
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
            )
            .clickable {
                onClickDespesa()
            },
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
                            fontSize = 11.5.sp,
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
                                fontSize = 9.5.sp,
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

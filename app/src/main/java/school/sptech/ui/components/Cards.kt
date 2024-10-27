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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import formatarData
import formatarDecimal
import formatarValorMonetario
import school.sptech.R
import school.sptech.data.model.Despesa
import school.sptech.data.model.Movimentos
import school.sptech.data.model.Produto
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
import transformarEmLocalDate
import java.time.LocalDate

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
    produto: Produto,
    isTelaInicio: Boolean,
    onClickCardProduto: () -> Unit,
    modifier: Modifier = Modifier
) {
    var exibirModalRepor by remember { mutableStateOf(false) }
    var exibirModalRetirar by remember { mutableStateOf(false) } // Controle do modal de retirada
    val listaValidades = produto.validades?.map { it.dtValidade ?: "" } ?: listOf()

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
            .clickable(onClick = onClickCardProduto)
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

                ButtonEstoque(qtdEstoque = produto.qtdEstoque ?: 0)

                if (isTelaInicio) {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = CircleShape,
                        onClick = { exibirModalRepor = true },
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
                    val buttonRetirarEnabled = produto.qtdEstoque ?: 0 > 0

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
                            onClick = { exibirModalRepor = true },
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
                ReporProductModal(
                    onDismiss = { exibirModalRepor = false },
                    produto = produto.nome ?: "",
                    quantidadeEstoque = produto.qtdEstoque ?: 0,
                    onConfirm = {
                        exibirModalRepor = false
                    },
                    datesFromBackend = listaValidades
                )
            }

            // Exibe o modal de retirada de produto
            if (exibirModalRetirar) {

                RetirarProductModal(
                    produto = produto.nome ?: "",
                    quantidadeEstoque = produto.qtdEstoque ?: 0,
                    onDismiss = { exibirModalRetirar = false },
                    onConfirm = {
                        exibirModalRetirar = false
                    },
                    datesFromBackend = listaValidades
                )
            }
        }
    }
}

@Composable
fun CardMovimentos(
    movimentos: Movimentos,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .drawBehind {
            val borderSize = 1.dp.toPx()
            val y = size.height - borderSize / 2

            drawLine(
                color = PretoOpacidade25,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = borderSize
            )
        }
    ) {
        Spacer(modifier = Modifier.size(12.dp))

        Row {
            Text(
                text = formatarData(movimentos.dtMovimentos),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = fontFamilyPoppins,
                letterSpacing = letterSpacingPrincipal,
                color = Cinza
            )
        }

        Spacer(modifier = Modifier.size(4.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            val isDespesa by remember { mutableStateOf(movimentos.tipoMovimentos == "Despesa") }

            Text(
                text = movimentos.nome,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamilyPoppins,
                letterSpacing = letterSpacingPrincipal,
                color = RoxoNubank
            )

            Text(
                text = stringResource(
                    id = R.string.valorMovimentos,
                    if (isDespesa) "-" else "+",
                    formatarDecimal(movimentos.valor.toFloat())
                ),
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = fontFamilyPoppins,
                letterSpacing = letterSpacingPrincipal,
                color = if (isDespesa) Vermelho else Azul
            )
        }
    }
}

@Composable
fun CardDespesa(despesa: Despesa, corTexto: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .border(1.dp, PretoOpacidade15, RoundedCornerShape(20.dp)),
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
                            Text(text = despesa.dtPagamento?.let { formatarData(it) } ?: "",
            //                text = despesa.dtPagamento ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                fontFamily = fontFamilyPoppins
                            )
                        }
                    }

                    Text(
                        text = despesa.nome?.capitalize() ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = corTexto,
                    )

                    Text(
                        text = despesa.valor?.let { formatarValorMonetario(it.toFloat()) } ?: "",
                        fontSize = 16.sp,
                        color = Vermelho,
                        fontWeight = FontWeight.Bold
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

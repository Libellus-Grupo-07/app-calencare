import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import school.sptech.ui.components.FormFieldWithLabel
import school.sptech.R
import school.sptech.data.model.CategoriaProduto
import school.sptech.ui.components.ButtonBackground
import school.sptech.ui.components.ButtonOutline
import school.sptech.ui.components.LabelInput
import school.sptech.ui.components.TituloLarge
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.CinzaOpacidade15
import school.sptech.ui.theme.CinzaOpacidade7
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.viewModel.DespesaViewModel
import school.sptech.ui.viewModel.ProdutoViewModel

@ExperimentalMaterial3Api
@Composable
fun FiltroModal(
    content: @Composable () -> Unit,
    onDismiss: () -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        windowInsets = WindowInsets(
            left = 0,
            top = 0,
            right = 0,
            bottom = 0
        ),
        containerColor = Branco,
        contentColor = Cinza
    ) {
        Column(
            modifier = Modifier.padding(bottom = 32.dp, start = 32.dp, end = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TituloLarge(titulo = "Filtro")

            content()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun FiltroEstoqueModal(
    produtoViewModel: ProdutoViewModel,
    onSalvar: () -> Unit = {},
    onLimpar: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val categorias = produtoViewModel.categoriasProduto
    val categoriasSelecionadas = remember {
        mutableStateListOf<String>().apply {
            addAll(produtoViewModel.filtro.categorias)
        }
    }

    FiltroModal(onDismiss = onDismiss, content = {
        Column {
            // Quantidade Disponível - Range Slider
            LabelInput(label = "Quantidade Disponível", isSmallInput = true)

            RangeSlider(
                value = produtoViewModel.filtro.rangeQtdEstoque,
                onValueChange = { range ->
                    produtoViewModel.filtro =
                        produtoViewModel.filtro.copy(rangeQtdEstoque = range)
                },
                valueRange = 0f..produtoViewModel.qtdMaximaEstoque,
                steps = 0,
                colors = SliderDefaults.colors(
                    thumbColor = RoxoNubank,
                    activeTrackColor = RoxoNubank,
                    inactiveTrackColor = CinzaOpacidade15
                )
            )

            Text(
                text = "${produtoViewModel.filtro.rangeQtdEstoque.start.toInt()} - ${produtoViewModel.filtro.rangeQtdEstoque.endInclusive.toInt()}",
                fontFamily = fontFamilyPoppins,
                color = Cinza,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp,
                modifier = Modifier.padding(start = 4.dp),
            )
        }

        Column {
            // Categoria
            LabelInput(label = "Categorias", isSmallInput = true)

            SelectableButtons(
                items = categorias.map { it.nome ?: "" },
                itemsSelecionados = mutableListOf<String>().apply {
                    addAll(categoriasSelecionadas)
                },
                onClick = {
                    if (categoriasSelecionadas.contains(it)) {
                        categoriasSelecionadas.remove(it)
                    } else {
                        categoriasSelecionadas.add(it)
                    }
                }
            )
        }

        FormFieldWithLabel(
            value = produtoViewModel.filtro.dtReposicao,
            onValueChange = {
                produtoViewModel.filtro = produtoViewModel.filtro.copy(dtReposicao = it)
            },
            label = "Data de Reposição",
            isDateInput = true,
            isSmallInput = true,
            enabledClearDate = true
        )

        FormFieldWithLabel(
            value = produtoViewModel.filtro.dtRetirada,
            onValueChange = {
                produtoViewModel.filtro = produtoViewModel.filtro.copy(dtRetirada = it)
            },
            label = "Data de Retirada",
            isDateInput = true,
            isSmallInput = true,
            enabledClearDate = true
        )

        FormFieldWithLabel(
            value = produtoViewModel.filtro.dtValidade,
            onValueChange = {
                produtoViewModel.filtro = produtoViewModel.filtro.copy(dtValidade = it)
            },
            label = "Data de Validade",
            isDateInput = true,
            isSmallInput = true,
            enabledClearDate = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ButtonOutline(
                iconId = R.mipmap.limpar,
                // isMediumButton = true,
                onClick = onLimpar,
                titulo = "Limpar",
            )

            Spacer(modifier = Modifier.width(6.dp))

            ButtonBackground(
                isMediumButton = true,
                onClick = {
                    produtoViewModel.filtro =
                        produtoViewModel.filtro.copy(categorias = categoriasSelecionadas)
                    onSalvar()
                },
                titulo = "Aplicar",
                cor = RoxoNubank
            )
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltroDespesaModal(
    onSalvar: () -> Unit,
    onLimpar: () -> Unit,
    onDismiss: () -> Unit,
    despesaViewModel: DespesaViewModel
) {
    var categorias = despesaViewModel.listaCategoriasDespesa.map { it.nome ?: "" }
    var categoriasSelecionadas = remember {
        mutableStateListOf<String>().apply {
            addAll(despesaViewModel.filtro.categorias)
        }
    }
    var formasPagamento =
        despesaViewModel.getListaDespesas().map { it.formaPagamento ?: "" }.distinct()

    var formasPagamentoSelecionadas = remember {
        mutableStateListOf<String>().apply {
            addAll(despesaViewModel.filtro.formasPagamento)
        }
    }

    FiltroModal(
        onDismiss = onDismiss,
        content = {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column {
                    LabelInput(label = "Categorias", isSmallInput = true)

                    SelectableButtons(
                        items = categorias,
                        itemsSelecionados = categoriasSelecionadas,
                        onClick = {
                            if (categoriasSelecionadas.contains(it)) {
                                categoriasSelecionadas.remove(it)
                            } else {
                                categoriasSelecionadas.add(it)
                            }
                        }
                    )
                }

                FormFieldWithLabel(
                    value = despesaViewModel.filtro.dtPagamento,
                    onValueChange = {
                        despesaViewModel.filtro = despesaViewModel.filtro.copy(dtPagamento = it)
                    },
                    label = "Data de Pagamento",
                    isDateInput = true,
                    isSmallInput = true,
                    enabledClearDate = true
                )

                Column {
                    LabelInput(label = "Formas de Pagamento", isSmallInput = true)

                    SelectableButtons(
                        items = formasPagamento,
                        itemsSelecionados = formasPagamentoSelecionadas,
                        onClick = {
                            if (formasPagamentoSelecionadas.contains(it)) {
                                formasPagamentoSelecionadas.remove(it)
                            } else {
                                formasPagamentoSelecionadas.add(it)
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(0.45f)) {
                        FormFieldWithLabel(
                            value = formatarDecimal(
                                despesaViewModel.filtro.valorMinimo,
                                isValueInput = true
                            ),
                            onValueChange = {
                                val valor = it.replace(",", "").replace(".", "")
                                despesaViewModel.filtro = despesaViewModel.filtro.copy(
                                    valorMinimo = valor.toDoubleOrNull() ?: 0.0
                                )
                            },
                            label = "Valor Mínimo",
                            isSmallInput = true,
                            isNumericInput = true,
                            isObrigatorio = false
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(0.45f)) {
                        FormFieldWithLabel(
                            value = formatarDecimal(
                                despesaViewModel.filtro.valorMaximo,
                                isValueInput = true
                            ),
                            onValueChange = {
                                val valor = it.replace(",", "").replace(".", "")
                                val valorFormatado = valor.toDoubleOrNull() ?: 0.0
                                despesaViewModel.filtro = despesaViewModel.filtro.copy(
                                    valorMaximo = valorFormatado
                                )
                            },
                            label = "Valor Máximo",
                            isSmallInput = true,
                            isNumericInput = true,
                            isObrigatorio = false
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    ButtonOutline(
                        iconId = R.mipmap.limpar,
                        isMediumButton = true,
                        onClick = onLimpar,
                        titulo = "Limpar",
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    ButtonBackground(
                        isMediumButton = true,
                        onClick = {
                            despesaViewModel.filtro = despesaViewModel.filtro.copy(
                                categorias = categoriasSelecionadas,
                                formasPagamento = formasPagamentoSelecionadas,
                            )
                            onSalvar()
                        },
                        titulo = "Aplicar",
                        cor = RoxoNubank
                    )
                }
            }
        }
    )
}

@JvmOverloads
@Composable
fun SelectableButtons(
    items: List<String> = listOf(),
    itemsSelecionados: MutableList<String> = mutableListOf(),
    onClick: (String) -> Unit = {}
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(items.size) { i ->
            ButtonBackground(
                enabledIcon = false,
                isSmallButton = true,
                onClick = {
                    onClick(items[i])
                },
                titulo = items[i],
                cor = if (itemsSelecionados.contains(items[i])) RoxoNubank else CinzaOpacidade7
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFiltroEstoqueModal() {
    FiltroEstoqueModal(produtoViewModel = viewModel())
}

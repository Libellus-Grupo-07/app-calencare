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
import school.sptech.ui.viewModel.ProdutoViewModel

@ExperimentalMaterial3Api
@Composable
fun FilterModal(
    produtoViewModel: ProdutoViewModel,
    onSalvar: () -> Unit = {},
    onLimpar: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val categorias = produtoViewModel.categoriasProduto
    val categoriasSelecionadas = remember {
        mutableStateListOf<CategoriaProduto>().apply { addAll(produtoViewModel.filtro.categorias) }
    }

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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TituloLarge(titulo = "Filtro")

            Column{
                // Quantidade Disponível - Range Slider
                LabelInput(label = "Quantidade Disponível", isSmallInput = true)

                RangeSlider(
                    value = produtoViewModel.filtro.rangeQtdEstoque,
                    onValueChange = { range -> produtoViewModel.filtro = produtoViewModel.filtro.copy(rangeQtdEstoque = range) },
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
                // Categorias
                LabelInput(label = "Categorias", isSmallInput = true)

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(categorias.size) { i ->
                        ButtonBackground(
                            enabledIcon = false,
                            isSmallButton = true,
                            onClick = {
                                if(categoriasSelecionadas.contains(categorias[i])){
                                    categoriasSelecionadas.remove(categorias[i])
                                } else{
                                    categoriasSelecionadas.add(categorias[i])
                                }
                            },
                            titulo = categorias[i].nome ?: "",
                            cor = if (categoriasSelecionadas.contains(categorias[i]))
                                RoxoNubank else CinzaOpacidade7
                        )
                    }
                }
            }

            FormFieldWithLabel(
                value = produtoViewModel.filtro.dtReposicao,
                onValueChange = { produtoViewModel.filtro = produtoViewModel.filtro.copy(dtReposicao = it) },
                label = "Data de Reposição",
                isDateInput = true,
                isSmallInput = true
            )

            FormFieldWithLabel(
                value = produtoViewModel.filtro.dtRetirada,
                onValueChange = { produtoViewModel.filtro = produtoViewModel.filtro.copy(dtRetirada = it) },
                label = "Data de Retirada",
                isDateInput = true,
                isSmallInput = true
            )

            FormFieldWithLabel(
                value = produtoViewModel.filtro.dtValidade,
                onValueChange = { produtoViewModel.filtro = produtoViewModel.filtro.copy(dtValidade = it) },
                label = "Data de Validade",
                isDateInput = true,
                isSmallInput = true
            )

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
                        produtoViewModel.filtro = produtoViewModel.filtro.copy(categorias = categoriasSelecionadas)
                        onSalvar()
                    },
                    titulo = "Aplicar",
                    cor = RoxoNubank
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFilterModal() {
    FilterModal(produtoViewModel = viewModel())
}

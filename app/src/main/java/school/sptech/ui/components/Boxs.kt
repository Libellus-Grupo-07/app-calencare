package school.sptech.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import getStringProduto
import school.sptech.R
import school.sptech.Routes
import school.sptech.data.model.Movimentacoes
import school.sptech.data.model.Produto
import school.sptech.ui.theme.CinzaOpacidade7
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.theme.letterSpacingPrincipal
import school.sptech.ui.viewModel.ValidadeViewModel

@Composable
fun BoxKpisEstoque(
    qtdProdutosEstoqueAlto: Int,
    qtdProdutosSemEstoque: Int,
    qtdProdutosRepostosNoDia: Int,
    qtdProdutosEstoqueBaixo: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
        Column {
            Row(modifier = modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Column(modifier = modifier.weight(0.4f)) {
                    CardKpi(
                        titulo = stringResource(id = R.string.produtoEstoqueAlto),
                        valor = "$qtdProdutosEstoqueAlto ${getStringProduto(qtdProdutosEstoqueAlto)}",
                        cor = "Verde"
                    )
                }

                Spacer(modifier = modifier.size(10.dp))

                Column(modifier = modifier.weight(0.4f)) {
                    CardKpi(
                        titulo = stringResource(id = R.string.produtoSemEstoque),
                        valor = "$qtdProdutosSemEstoque ${getStringProduto(qtdProdutosSemEstoque)}",
                        cor = "Vermelho"
                    )
                }
            }

            Spacer(modifier = modifier.size(8.dp))

            Row {
                Column(modifier = modifier.weight(0.5f)) {
                    CardKpi(
                        titulo = stringResource(id = R.string.produtosRepostosDia),
                        valor = "$qtdProdutosRepostosNoDia ${
                            getStringProduto(
                                qtdProdutosRepostosNoDia
                            )
                        }",
                        cor = "Azul"
                    )
                }

                Spacer(modifier = modifier.size(10.dp))

                Column(modifier = modifier.weight(0.5f)) {
                    CardKpi(
                        titulo = stringResource(id = R.string.produtosEstoqueBaixo),
                        valor = "$qtdProdutosEstoqueBaixo ${getStringProduto(qtdProdutosEstoqueBaixo)}",
                        cor = "Laranja"
                    )
                }
            }
        }
    }
}

@Composable
fun BoxProdutos(
    navController: NavController,
    produtos: List<Produto>,
    titulo: String,
    isTelaInicio: Boolean,
    validadeViewModel: ValidadeViewModel,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Row {
                Text(
                    text = titulo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamilyPoppins,
                    letterSpacing = letterSpacingPrincipal,
                    color = Preto
                )
            }

            Spacer(modifier = modifier.size(21.dp))

            if (produtos.isEmpty()) {
                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                   TextoNenhumItemCadastrado(texto = "Nenhum produto encontrado")
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(produtos) { produto ->
                        CardProduto(
                            produto = produto,
                            isTelaInicio = isTelaInicio,
                            onClickCardProduto = {
                                navController.navigate("${Routes.InformacoesProduto.route}/${produto.id}")
                            },
                       //     validadeViewModel = validadeViewModel,
                            modifier = modifier.fillMaxSize(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BoxMovimentacoes(movimentacoes: List<Movimentacoes>, onClickCard: (Movimentacoes) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TituloLarge(titulo = stringResource(id = R.string.movimentos))

        if (movimentacoes.isEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextoNenhumItemCadastrado(texto = "Nenhum movimento encontrado")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movimentacoes) { movimento ->
                    CardMovimentacoes(movimento, onClick = {
                        onClickCard(movimento)
                    })
                }
            }
        }
    }
}

@Composable
fun BoxPerfil(telaAtual: String, onChangeTelaAtual: (String) -> Unit) {
    // Imagem de perfil centralizada
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .padding(vertical = 0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ft),
            contentDescription = "Imagem de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp)
        )
    }

    Spacer(modifier = Modifier.size(16.dp))

    // Botões para alternar dados da empresa e pessoais
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        Alignment.CenterVertically,

        ) {
        ButtonBackground(
            titulo = stringResource(R.string.dadosEmpresa),
            onClick = { onChangeTelaAtual(Routes.DadosEmpresa.route) },
            cor = if (telaAtual == Routes.DadosEmpresa.route) RoxoNubank else CinzaOpacidade7,
            enabledIcon = false
        )

        Spacer(modifier = Modifier.size(4.dp))

        ButtonBackground(
            titulo = stringResource(R.string.dadosPessoais),
            onClick = { onChangeTelaAtual(Routes.DadosPessoais.route) },
            cor = if (telaAtual == Routes.DadosPessoais.route) RoxoNubank else CinzaOpacidade7,
            enabledIcon = false
        )
    }

}
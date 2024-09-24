package school.sptech.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import school.sptech.R

@Composable
fun BoxKpisEstoque(
    qtdProdutosEstoqueAlto: Int,
    qtdProdutosSemEstoque: Int,
    qtdProdutosRepostosNoDia: Int,
    qtdProdutosEstoqueBaixo: Int,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
        Column {
            Row(modifier = modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Column(modifier = modifier.weight(0.4f)) {
                    CardKpi(
                        titulo = stringResource(id = R.string.produtoEstoqueAlto),
                        valor = "$qtdProdutosEstoqueAlto produtos",
                        cor = "Verde"
                    )
                }

                Spacer(modifier = modifier.size(10.dp))

                Column(modifier = modifier.weight(0.4f)) {
                    CardKpi(
                        titulo = stringResource(id = R.string.produtoSemEstoque),
                        valor = "$qtdProdutosSemEstoque produto",
                        cor = "Vermelho"
                    )
                }
            }

            Spacer(modifier = modifier.size(8.dp))

            Row {
                Column(modifier = modifier.weight(0.5f)) {
                    CardKpi(
                        titulo = stringResource(id = R.string.produtosRepostosDia),
                        valor = "$qtdProdutosRepostosNoDia produtos",
                        cor = "Azul"
                    )
                }

                Spacer(modifier = modifier.size(10.dp))

                Column(modifier = modifier.weight(0.5f)) {
                    CardKpi(
                        titulo = stringResource(id = R.string.estoqueBaixo),
                        valor = "$qtdProdutosEstoqueBaixo produtos",
                        cor = "Laranja"
                    )
                }
            }
        }
    }
}


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
import androidx.compose.ui.unit.dp

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
                        titulo = "Produtos com Estoque Alto",
                        valor = "$qtdProdutosEstoqueAlto produtos",
                        cor = "Verde"
                    )
                }

                Spacer(modifier = modifier.size(10.dp))

                Column(modifier = modifier.weight(0.4f)) {
                    CardKpi(
                        titulo = "Produtos sem Estoque",
                        valor = "$qtdProdutosSemEstoque produto",
                        cor = "Vermelho"
                    )
                }
            }

            Spacer(modifier = modifier.size(8.dp))

            Row {
                Column(modifier = modifier.weight(0.5f)) {
                    CardKpi(
                        titulo = "Produtos Repostos no Dia",
                        valor = "$qtdProdutosRepostosNoDia produtos",
                        cor = "Azul"
                    )
                }

                Spacer(modifier = modifier.size(10.dp))

                Column(modifier = modifier.weight(0.5f)) {
                    CardKpi(
                        titulo = "Produtos com Estoque Baixo",
                        valor = "$qtdProdutosEstoqueBaixo produtos",
                        cor = "Laranja"
                    )
                }
            }
        }
    }
}


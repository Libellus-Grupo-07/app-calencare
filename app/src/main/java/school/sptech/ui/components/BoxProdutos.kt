package school.sptech.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.data.model.Produto
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.fontFamily
import school.sptech.ui.theme.letterSpacingPrincipal

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BoxProdutos(produtos: MutableList<Produto>, titulo:String){
    Box(modifier = Modifier.fillMaxWidth()){
        Column {
            Row {
                Text(
                    text = titulo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    letterSpacing = letterSpacingPrincipal,
                    color = Preto
                )
            }

            Spacer(modifier = Modifier.size(21.dp))
            FlowRow(modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                maxItemsInEachRow = 2) {
                repeat(2 * (produtos.size / 2)){ i ->
                    CardProduto(
                        nome = produtos[i].nome,
                        categoria = produtos[i].categoria,
                        qtdEstoque = produtos[i].qtdEstoque,
                        isTelaInicio = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
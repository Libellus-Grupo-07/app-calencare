package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import school.sptech.R
import school.sptech.data.model.CategoriaProduto
import school.sptech.data.model.Produto
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxProdutos
import school.sptech.ui.components.TopBarEstoque

class Estoque : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaEstoque(rememberNavController())
        }
    }
}

@Composable
fun TelaEstoque(navController: NavController){
    val categoriaUnha = CategoriaProduto(nome = "Unha")
    val categoriaCabelo = CategoriaProduto(nome = "Cabelo")
    val categoriaMaquiagem = CategoriaProduto(nome = "Maquiagem")

    val listaProdutos = remember {
        mutableListOf(
            Produto(nome = "Esmalte Azul Metálico Risqué 8ml", categoriaProdutoNome = categoriaUnha.nome, qtdEstoque = 0),
            Produto(nome ="Shampoo Mais Lisos Wella 350ml", categoriaProdutoNome = categoriaCabelo.nome, qtdEstoque = 1),
            Produto(nome ="Condicionador Wella 350ml", categoriaProdutoNome = categoriaCabelo.nome, qtdEstoque = 1),
            Produto(nome ="Máscara de Cílios Volume Up Vult 8g", categoriaProdutoNome = categoriaMaquiagem.nome, qtdEstoque = 3),
            Produto(nome ="Spray Keune Style Dry Texturizer 300ml", categoriaProdutoNome = categoriaCabelo.nome, qtdEstoque = 4),
            Produto(nome ="Base Líquida Estée Lauder  SFS-10", categoriaProdutoNome = categoriaMaquiagem.nome, qtdEstoque = 6),
        )
    }

    Background()

    Column( modifier = Modifier.padding(vertical = 24.dp)) {
        TopBarEstoque(navController = navController)
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Column {
                BoxProdutos(
                    produtos = listaProdutos,
                    titulo = stringResource(id = R.string.estoque),
                    isTelaInicio = false,
                    modifier = Modifier.fillMaxWidth(),
                    navController = navController
                )
            }
        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EstoquePreview() {
    TelaEstoque(rememberNavController())
}
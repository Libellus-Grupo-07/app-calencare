package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import school.sptech.R
import school.sptech.data.model.CategoriaProduto
import school.sptech.data.model.Produto
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxKpisEstoque
import school.sptech.ui.components.BoxProdutos
import school.sptech.ui.components.TopBarInicio
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.viewModel.UsuarioViewModel

class TelaInicial : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalencareAppTheme {
                TelaInicio(navController = rememberNavController())
            }
        }
    }
}

@Composable
fun TelaInicio(
    usuarioViewModel: UsuarioViewModel = viewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    usuarioViewModel.getFuncionario(1)
    val usuario = usuarioViewModel.usuario

    val categoriaUnha = CategoriaProduto(nome = "Unha")
    val categoriaCabelo = CategoriaProduto(nome = "Cabelo")
    val categoriaMaquiagem = CategoriaProduto(nome = "Maquiagem")

    val listaProdutos = remember {
        mutableListOf(
            Produto(
                nome = "Esmalte Azul Metálico Risqué 8ml",
                categoriaProdutoNome = categoriaUnha.nome,
                qtdEstoque = 0
            ),
            Produto(
                nome = "Shampoo Mais Lisos Wella 350ml",
                categoriaProdutoNome = categoriaCabelo.nome,
                qtdEstoque = 1
            ),
            Produto(
                nome = "Condicionador Wella 350ml",
                categoriaProdutoNome = categoriaCabelo.nome,
                qtdEstoque = 1
            ),
            Produto(
                nome = "Máscara de Cílios Volume Up Vult 8g",
                categoriaProdutoNome = categoriaMaquiagem.nome,
                qtdEstoque = 3
            ),
            Produto(
                nome = "Spray Keune Style Dry Texturizer 300ml",
                categoriaProdutoNome = categoriaCabelo.nome,
                qtdEstoque = 4
            ),
            Produto(
                nome = "Base Líquida Estée Lauder  SFS-10",
                categoriaProdutoNome = categoriaMaquiagem.nome,
                qtdEstoque = 6
            ),
        )
    }

    if (usuarioViewModel.deuErro) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(Color(228, 3, 80, 210))
                .zIndex(1f)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "⚠\uFE0F Ops! Alguma coisa deu errado. \r\nTente novamente ${usuarioViewModel.erro}",
                    color = Color.Yellow,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        }
        // agenda o "fechamento" da janela de mensagem para daqui a 7 segundos
        LaunchedEffect("alerta-topo") { // o texto do argumento é o "nome", a "chave" do timer
            delay(7000)
            usuarioViewModel.deuErro = false
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Background()
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .verticalScroll(state = ScrollState(1))
            ) {
                TopBarInicio(usuario, navController = navController)
                Spacer(modifier = Modifier.size(21.dp))
                BoxKpisEstoque(
                    qtdProdutosEstoqueAlto = 25,
                    qtdProdutosSemEstoque = 1,
                    qtdProdutosRepostosNoDia = 0,
                    qtdProdutosEstoqueBaixo = 5
                )
                Spacer(modifier = Modifier.size(21.dp))
                BoxProdutos(
                    produtos = listaProdutos,
                    titulo = stringResource(id = R.string.produtosComQuantidadeBaixa),
                    isTelaInicio = true,
                    navController = navController
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaInicialPreview() {
    CalencareAppTheme {
        TelaInicio(navController = rememberNavController())
    }
}

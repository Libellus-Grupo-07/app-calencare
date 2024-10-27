package school.sptech.ui.screens

import android.annotation.SuppressLint
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import school.sptech.R
import school.sptech.data.model.Produto
import school.sptech.preferencesHelper
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxKpisEstoque
import school.sptech.ui.components.BoxProdutos
import school.sptech.ui.components.TopBarInicio
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Preto
import school.sptech.ui.viewModel.ProdutoViewModel
import school.sptech.ui.viewModel.UsuarioViewModel
import school.sptech.ui.viewModel.ValidadeViewModel

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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TelaInicio(
    usuarioViewModel: UsuarioViewModel = viewModel(),
    produtoViewModel: ProdutoViewModel = viewModel(),
    validadeViewModel: ValidadeViewModel = viewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        // Inicializa o usuário e a empresa
        usuarioViewModel.getFuncionario(preferencesHelper.getIdUsuario())
        val idEmpresa = preferencesHelper.getIdEmpresa()

        if (idEmpresa == -1 || idEmpresa == 0) {
            preferencesHelper.saveIdEmpresa(usuarioViewModel.usuario.empresa?.id ?: 0)
        }

        // Obtém a lista de produtos com base no id da empresa do usuário
//        val produtos = produtoViewModel.getProdutos(usuarioViewModel.usuario?.empresa?.id ?: 0)

        // Atualiza cada produto com suas respectivas validades e quantidade em estoque
//        produtos.forEach { produto ->
//            produto.validades = validadeViewModel.getValidades(produto.id!!)
//            produto.qtdEstoque = validadeViewModel.getTotalEstoqueProduto(produto.id!!)
//        }
    }

    val listaProdutos = produtoViewModel.getProdutos(usuarioViewModel.usuario?.empresa?.id ?: 0)
    val usuario = usuarioViewModel.usuario
    
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
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Background()
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 8.dp)
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

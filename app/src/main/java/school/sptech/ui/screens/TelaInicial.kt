package school.sptech.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import school.sptech.R
import school.sptech.preferencesHelper
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxKpisEstoque
import school.sptech.ui.components.BoxProdutos
import school.sptech.ui.components.TopBarInicio
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.viewModel.MovimentacaoValidadeViewModel
import school.sptech.ui.viewModel.ProdutoViewModel
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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TelaInicio(
    usuarioViewModel: UsuarioViewModel = viewModel(),
    produtoViewModel: ProdutoViewModel = viewModel(),
    movimentacaoValidadeViewModel: MovimentacaoValidadeViewModel = viewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        usuarioViewModel.getFuncionario(preferencesHelper.getIdUsuario())
        produtoViewModel.getProdutos(preferencesHelper.getIdEmpresa())
        preferencesHelper.saveIdEmpresa(usuarioViewModel.usuario.empresa?.id ?: preferencesHelper.getIdEmpresa())

//        movimentacaoValidadeViewModel.getKpisEstoque(preferencesHelper.getIdEmpresa())
    }

    val idEmpresa = usuarioViewModel.usuario.empresa?.id ?: preferencesHelper.getIdEmpresa()

    val usuario = usuarioViewModel.usuario

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
                    qtdProdutosEstoqueAlto = movimentacaoValidadeViewModel.getQuantidadeProdutosEstoqueAlto(idEmpresa),
                    qtdProdutosSemEstoque = movimentacaoValidadeViewModel.getQuantidadeProdutosSemEstoque(idEmpresa),
                    qtdProdutosRepostosNoDia = movimentacaoValidadeViewModel.getQuantidadeProdutosRepostosNoDia(idEmpresa),
                    qtdProdutosEstoqueBaixo = movimentacaoValidadeViewModel.getQuantidadeProdutosEstoqueBaixo(idEmpresa)
                )

                Spacer(modifier = Modifier.size(21.dp))

                BoxProdutos(
                    produtos = produtoViewModel.getListaProdutos(),
                    titulo = stringResource(id = R.string.produtosComQuantidadeBaixa),
                    isTelaInicio = true,
                    navController = navController
                )
            }
        }
    }

    if (usuarioViewModel.deuErro) {
        AlertError(msg = "Ops! Algo deu errado. Tente novamente mais tarde.")

        LaunchedEffect("erro") {
            delay(6000)
            usuarioViewModel.deuErro = false
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

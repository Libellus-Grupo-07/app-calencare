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
import school.sptech.data.service.MovimentacaoValidadeService
import school.sptech.preferencesHelper
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxKpisEstoque
import school.sptech.ui.components.BoxProdutos
import school.sptech.ui.components.TopBarInicio
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Preto
import school.sptech.ui.viewModel.EmpresaViewModel
import school.sptech.ui.viewModel.MovimentacaoValidadeViewModel
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
    movimentacaoValidadeViewModel: MovimentacaoValidadeViewModel = viewModel(),
    validadeViewModel: ValidadeViewModel = viewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        // Inicializa o usuário e a empresa
        usuarioViewModel.getFuncionario(preferencesHelper.getIdUsuario())
        movimentacaoValidadeViewModel.getKpisEstoque()
    }

    val idEmpresa = usuarioViewModel.usuario.empresa?.id ?: preferencesHelper.getIdEmpresa()
    preferencesHelper.saveIdEmpresa(idEmpresa)

    val listaProdutos = produtoViewModel.getProdutos(idEmpresa)
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
                    qtdProdutosEstoqueAlto = movimentacaoValidadeViewModel.qtdProdutosEstoqueAlto ?: 0,
                    qtdProdutosSemEstoque = movimentacaoValidadeViewModel.qtdProdutosSemEstoque ?: 0,
                    qtdProdutosRepostosNoDia = movimentacaoValidadeViewModel.qtdProdutosRepostosNoDia ?: 0,
                    qtdProdutosEstoqueBaixo = movimentacaoValidadeViewModel.qtdProdutosEstoqueBaixo ?: 0,
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

    if(usuarioViewModel.deuErro){
        AlertError(msg = usuarioViewModel.erro)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaInicialPreview() {
    CalencareAppTheme {
        TelaInicio(navController = rememberNavController())
    }
}

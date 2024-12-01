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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import school.sptech.R
import school.sptech.data.model.Empresa
import school.sptech.data.model.Funcionario
import school.sptech.dataStoreRepository
import school.sptech.di.UserSession
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.AlertSuccess
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxKpisEstoque
import school.sptech.ui.components.BoxProdutos
import school.sptech.ui.components.TopBarInicio
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.viewModel.MovimentacaoValidadeViewModel
import school.sptech.ui.viewModel.NotificacaoEstoqueViewModel
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
    usuarioViewModel: UsuarioViewModel = koinViewModel(),
    produtoViewModel: ProdutoViewModel = koinViewModel(),
    validadeViewModel: ValidadeViewModel = viewModel(),
    movimentacaoValidadeViewModel: MovimentacaoValidadeViewModel = viewModel(),
    notificacaoEstoqueViewModel: NotificacaoEstoqueViewModel = viewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        val idUser = dataStoreRepository.getUserId()

        GlobalScope.launch {
            if (!dataStoreRepository.alreadySaved()) {
                usuarioViewModel.getFuncionario(idUser)

                delay(1000)
                dataStoreRepository.saveUser(
                    UserSession(
                        idUser = idUser,
                        idEmpresa = usuarioViewModel.usuario.empresa?.id ?: 0,
                        nome = usuarioViewModel.usuario.nome ?: "",
                        nomeEmpresa = usuarioViewModel.usuario.empresa?.razaoSocial ?: ""
                    )
                )
            } else {
                usuarioViewModel.usuario = Funcionario(
                    id = dataStoreRepository.getUser().idUser,
                    nome = dataStoreRepository.getUser().nome,
                    empresa = Empresa(
                        id = dataStoreRepository.getEmpresaId(),
                        razaoSocial = dataStoreRepository.getUser().nomeEmpresa
                    )
                )
            }

            produtoViewModel.getProdutosAlertaEstoque(dataStoreRepository.getEmpresaId())
        }
    }

    val usuario = usuarioViewModel.usuario
    var idEmpresa = usuarioViewModel.usuario.empresa?.id ?: 0
    val existNotificacaoNaoLida = notificacaoEstoqueViewModel.existNotificacaoNaoLida()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Background()
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                TopBarInicio(usuario = usuario, existNotificacaoNaoLida = existNotificacaoNaoLida, navController = navController)
                Spacer(modifier = Modifier.size(21.dp))

                BoxKpisEstoque(
                    qtdProdutosEstoqueAlto = movimentacaoValidadeViewModel.getQuantidadeProdutosEstoqueAlto(
                        idEmpresa
                    ),
                    qtdProdutosSemEstoque = movimentacaoValidadeViewModel.getQuantidadeProdutosSemEstoque(
                        idEmpresa
                    ),
                    qtdProdutosRepostosNoDia = movimentacaoValidadeViewModel.getQuantidadeProdutosRepostosNoDia(
                        idEmpresa
                    ),
                    qtdProdutosEstoqueBaixo = movimentacaoValidadeViewModel.getQuantidadeProdutosEstoqueBaixo(
                        idEmpresa
                    )
                )

                Spacer(modifier = Modifier.size(21.dp))

                BoxProdutos(
                    produtos = produtoViewModel.getListaProdutosAlertaEstoque(),
                    validadeViewModel = validadeViewModel,
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

    if (!validadeViewModel.deuErro && validadeViewModel.erro.isNotEmpty()) {
        AlertSuccess(msg = "Estoque atualizado com sucesso!")

        LaunchedEffect("sucesso") {
            produtoViewModel.getProdutosAlertaEstoque(idEmpresa)
            delay(6000)
            validadeViewModel.erro = ""
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

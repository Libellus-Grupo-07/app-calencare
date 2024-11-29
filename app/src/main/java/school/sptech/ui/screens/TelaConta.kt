package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import school.sptech.Routes
import school.sptech.dataStoreRepository
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.AlertSuccess
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxPerfil
import school.sptech.ui.components.FormDadosEmpresa
import school.sptech.ui.components.FormDadosPessoais
import school.sptech.ui.components.LabelInput
import school.sptech.ui.components.ModalConfirmarSair
import school.sptech.ui.components.ModalEditarEndereco
import school.sptech.ui.components.TopBarConta
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.viewModel.EmpresaViewModel
import school.sptech.ui.viewModel.EnderecoViewModel
import school.sptech.ui.viewModel.UsuarioViewModel

class TelaConta : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TelaContaScreen(
                        navController = rememberNavController(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TelaContaScreen(
    usuarioViewModel: UsuarioViewModel = viewModel(),
    enderecoViewModel: EnderecoViewModel = viewModel(),
    empresaViewModel: EmpresaViewModel = viewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var idEmpresa = 0

    LaunchedEffect(Unit) {
        idEmpresa = dataStoreRepository.getEmpresaId()

        usuarioViewModel.getFuncionario(dataStoreRepository.getUserId())
        empresaViewModel.getEmpresa(dataStoreRepository.getEmpresaId())
        enderecoViewModel.getEndereco(dataStoreRepository.getEmpresaId())
    }

    val usuario = usuarioViewModel.usuario
    val empresa = empresaViewModel.empresa
    val endereco = enderecoViewModel.endereco
    var exibirModalSair by remember { mutableStateOf(false) }
    var exibirModalEndereco by remember { mutableStateOf(false) }

    var telaAtual by remember {
        mutableStateOf(navController.currentBackStackEntry?.destination?.route)
    }
    var enabledButtonSalvar by remember { mutableStateOf(true) }

    Scaffold { contentPadding ->
        Background()
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Linha para os botões "Voltar", "Sair da Conta" e "Salvar"

            TopBarConta(
                onClickSalvar = {
                    empresaViewModel.atualizarEmpresa()
                    usuarioViewModel.atualizarFuncionario()
                    enderecoViewModel.atualizarEndereco()
                },
                onClickSair = {
                    exibirModalSair = true
                },
                navController = navController,
                enabledButtonSalvar = enabledButtonSalvar
            )

            BoxPerfil(
                telaAtual = telaAtual ?: "",
                onChangeTelaAtual = { tela ->
                    telaAtual = tela
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedContent(
                telaAtual,
            ) { targetState ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .verticalScroll(ScrollState(1)),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    when (targetState) {
                        Routes.DadosEmpresa.route -> {
                            FormDadosEmpresa(
                                empresaViewModel = empresaViewModel,
                                empresa = empresa,
                                endereco = endereco,
                                onClickInputEndereco = {
                                    exibirModalEndereco = true
                                }
                            )
                        }

                        else -> {
                            enderecoViewModel.erro = ""
                            enderecoViewModel.deuErro = false
                            FormDadosPessoais(usuario, usuarioViewModel)
                        }
                    }
                }
            }
        }

        if (exibirModalSair) {
            ModalConfirmarSair(
                onConfirm = {
                    dataStoreRepository.logout()

                    exibirModalSair = false
                    navController.navigate(Routes.Login.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onDismiss = {
                    exibirModalSair = false
                }
            )
        }

        if(exibirModalEndereco){
            ModalEditarEndereco(
                onDismiss = {
                    exibirModalEndereco = false
                    enderecoViewModel.getEndereco(idEmpresa)
                },
                onConfirm = {
                    enderecoViewModel.atualizarEndereco()

                    if(!enderecoViewModel.deuErro){
                        exibirModalEndereco = false
                    }
                },
                enderecoViewModel = enderecoViewModel
            )
        }

        if (usuarioViewModel.deuErro || empresaViewModel.deuErro || enderecoViewModel.deuErro) {
            AlertError(msg =
                if (usuarioViewModel.deuErro) {
                    usuarioViewModel.erro
                } else
                    empresaViewModel.erro
            )

            LaunchedEffect("error") {
                delay(5000)
                usuarioViewModel.deuErro = false
                usuarioViewModel.erro = ""
                empresaViewModel.deuErro = false
                empresaViewModel.erro = ""
                enderecoViewModel.deuErro = false
                enderecoViewModel.erro = ""
            }
        } else if(
            usuarioViewModel.erro.isNotEmpty() &&
            empresaViewModel.erro.isNotEmpty()) {
            AlertSuccess(msg = "Informações atualizadas com sucesso!")

            LaunchedEffect("sucess") {
                delay(5000)
                navController.popBackStack()
            }
        } else if(enderecoViewModel.erro.isNotEmpty() && !enderecoViewModel.deuErro){
            AlertSuccess(
                msg = "Endereço atualizado com sucesso!",
                onClick = { enderecoViewModel.erro = "" }
            )

            LaunchedEffect("sucess") {
                delay(5000)
                enderecoViewModel.erro = ""
                enderecoViewModel.deuErro = false
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalencareAppTheme {
        TelaContaScreen(navController = rememberNavController())
    }
}
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
import school.sptech.Routes
import school.sptech.data.model.Funcionario
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxPerfil
import school.sptech.ui.components.FormDadosEmpresa
import school.sptech.ui.components.FormDadosPessoais
import school.sptech.ui.components.TopBarConta
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.viewModel.EmpresaViewModel
import school.sptech.ui.viewModel.UsuarioViewModel

class Conta : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TelaConta(
                        navController = rememberNavController(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TelaConta(
    usuarioViewModel: UsuarioViewModel = viewModel(),
    empresaViewModel: EmpresaViewModel = viewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    usuarioViewModel.getFuncionario(1)
    val usuario = usuarioViewModel.usuario
    empresaViewModel.getEmpresa(usuario.empresaId ?: 1)
    val empresa = empresaViewModel.empresa

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
                onClickSalvar = {},
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (targetState) {
                    Routes.DadosEmpresa.route -> {
                        FormDadosEmpresa(empresa)
                    }

                    else -> {
                        FormDadosPessoais(usuario)
                    }
                }
            }
        }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalencareAppTheme {
        TelaConta(navController = rememberNavController())
    }
}
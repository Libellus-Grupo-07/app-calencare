package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import school.sptech.R
import school.sptech.ui.components.Background
import school.sptech.ui.components.CardNotificacoes
import school.sptech.ui.components.TextoNenhumItemCadastrado
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.viewModel.NotificacaoEstoqueViewModel

class TelaNotificacoes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                TelaNotificacoesScreen(navController = rememberNavController())
            }
        }
    }
}

@Composable
fun TelaNotificacoesScreen(
    viewModel: NotificacaoEstoqueViewModel = viewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect("notificacoes") {
        viewModel.getNotificacoesEstoque()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopBarVoltar(
                    navController = navController,
                    titulo = stringResource(R.string.notificacoes),
                    isNotificacoes = true,
                    onClickAction = {
                        viewModel.marcarTodasComoLida()
                    }
                )
            }

        ) { innerPadding ->
            Background()

            Column(modifier = Modifier.padding(innerPadding)) {
                if (viewModel.notificacoes.isEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 110.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextoNenhumItemCadastrado(texto = "Não há notificações")
                    }
                } else
                    LazyColumn(
                        modifier = modifier.padding(4.dp)
                    ) {
                        items(viewModel.notificacoes.size) { i ->
                            CardNotificacoes(
                                notificacao = viewModel.notificacoes[i],
                                onClick = {
                                    viewModel.marcarComoLida(viewModel.notificacoes[i].id ?: 0)
                                }
                            )
                        }
                    }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview2() {
    CalencareAppTheme {
        TelaNotificacoesScreen(navController = rememberNavController())
    }
}
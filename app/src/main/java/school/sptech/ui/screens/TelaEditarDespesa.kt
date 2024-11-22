package school.sptech.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.R
import school.sptech.ui.components.Background
import school.sptech.ui.components.FormButtons
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.viewModel.DespesaViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import school.sptech.Routes
import school.sptech.navigation.NavBar
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.AlertSuccess
import kotlin.coroutines.cancellation.CancellationException

class TelaEditarDespesa : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                TelaEditDespesa()
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TelaEditDespesa(
    viewModel: DespesaViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect("categorias") {
        viewModel.getCategoriasDespesa()
    }

    Scaffold(
        topBar = {
            TopBarVoltar(
                navController = navController,
                titulo = stringResource(R.string.informacoes_despesa),
                actions = {
                    IconButton(onClick = { viewModel.excluirDespesa() }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Black)
                    }
                }
            )
        }
    ) { paddingValues ->
        Background()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            DespesaForm(viewModel)
            FormButtons(
                onCancelClick = { navController.popBackStack() },
                onAddClick = {
                    viewModel.salvarDespesa()
                },
                addButtonText = stringResource(R.string.salvar)
            )
        }

        if (viewModel.deuErro) {
            AlertError(msg = viewModel.erro)

            LaunchedEffect(key1 = "success") {
                delay(5000)
                viewModel.deuErro = false
            }
        }

        if (!viewModel.deuErro && viewModel.erro.isNotEmpty()) {
            AlertSuccess(msg = "Despesa salva com sucesso!")

            DisposableEffect(key1 = "Sucess") {
                val job = CoroutineScope(Dispatchers.Main).launch {
                    try {
                        delay(3000)
                        viewModel.erro = ""
                        navController.navigate(NavBar.Financas.route) {
                            popUpTo(Routes.EditarDespesa.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }

                    } catch (e: CancellationException) {
                        throw e
                    }
                }
                onDispose {
                    job.cancel()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaEditarDespesaPreview() {
    CalencareAppTheme {
        TelaEditDespesa()
    }
}
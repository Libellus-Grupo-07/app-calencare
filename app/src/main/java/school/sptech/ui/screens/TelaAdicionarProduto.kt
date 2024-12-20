package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import school.sptech.R
import school.sptech.Routes
import school.sptech.navigation.NavBar
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.AlertSuccess
import school.sptech.ui.components.Background
import school.sptech.ui.components.DropdownFieldWithLabel
import school.sptech.ui.components.FormButtons
import school.sptech.ui.components.FormFieldWithLabel
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Preto
import school.sptech.ui.viewModel.ProdutoViewModel
import kotlin.coroutines.cancellation.CancellationException

class TelaAdicionarProduto : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalencareAppTheme {
                TelaAdicionarProdutoScreen()
            }
        }
    }
}

@Composable
fun TelaAdicionarProdutoScreen(
    viewModel: ProdutoViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect(Unit) {
        viewModel.getCategoriasProduto()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopBarVoltar(
                    navController = navController,
                    titulo = stringResource(id = R.string.adicionarProduto)
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
                var nomePreenchido by remember { mutableStateOf(true) }
                var marcaPreenchida by remember { mutableStateOf(true) }
                var categoriaPreenchida by remember { mutableStateOf(true) }
                var descricaoPreenchida by remember { mutableStateOf(true) }

                Spacer(modifier = Modifier.height(4.dp))

                Column(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(4.dp)) {
                    FormFieldWithLabel(
                        value = viewModel.produto.nome ?: "",
                        onValueChange = {
                            viewModel.produto = viewModel.produto.copy(nome = it)
                            nomePreenchido = it.isNotEmpty()
                        },
                        label = stringResource(R.string.nome),
                        error = !nomePreenchido
                    )

                    FormFieldWithLabel(
                        value = viewModel.produto.marca ?: "",
                        onValueChange = {
                            viewModel.produto = viewModel.produto.copy(marca = it)
                            marcaPreenchida = it.isNotEmpty()
                        },
                        label = stringResource(R.string.marca),
                        error = !marcaPreenchida
                    )

                    DropdownFieldWithLabel(
                        value = viewModel.categoriaProduto.nome ?: "",
                        onValueChange = {
                            viewModel.categoriaProduto = viewModel.categoriaProduto.copy(nome = it)
                            categoriaPreenchida = it.isNotEmpty()
                        },
                        label = stringResource(R.string.categoria),
                        options = viewModel.categoriasProduto.map { it.nome ?: "" },
                        error = !categoriaPreenchida
                    )

                    FormFieldWithLabel(
                        value = viewModel.produto.descricao ?: "",
                        onValueChange = {
                            viewModel.produto = viewModel.produto.copy(descricao = it)
                            descricaoPreenchida = it.isNotEmpty()
                        },
                        label = stringResource(R.string.descricao),
                        isMultiline = true,
                        error = !descricaoPreenchida
                    )
                }

                FormButtons(
                    onAddClick = {
                        nomePreenchido = viewModel.produto.nome?.isNotEmpty() == true
                        marcaPreenchida = viewModel.produto.marca?.isNotEmpty() == true
                        categoriaPreenchida = viewModel.categoriaProduto.nome?.isNotEmpty() == true
                        descricaoPreenchida = viewModel.produto.descricao?.isNotEmpty() == true

                        if (
                            nomePreenchido &&
                            marcaPreenchida &&
                            categoriaPreenchida &&
                            descricaoPreenchida
                        ) {
                            viewModel.adicionarProduto()
                        }
                    },
                    onCancelClick = { navController.popBackStack() }
                )
            }
        }
    }

    if (viewModel.deuErro) {
        AlertError(msg = viewModel.mensagem)

        LaunchedEffect("error") {
            delay(6000)
            viewModel.deuErro = false
            viewModel.mensagem = ""
        }
    }

    if (!viewModel.deuErro && viewModel.mensagem.isNotEmpty()) {
        AlertSuccess("Produto adicionado com sucesso!")

        DisposableEffect(key1 = "Sucess") {
            val job = CoroutineScope(Dispatchers.Main).launch {
                try {
                    delay(3000)
                    viewModel.mensagem = ""
                    navController.navigate(NavBar.Estoque.route) {
                        popUpTo(Routes.AdicionarProduto.route) {
                            inclusive = true
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaAdicionarProdutoPreview() {
    CalencareAppTheme {
        TelaAdicionarProdutoScreen()
    }
}

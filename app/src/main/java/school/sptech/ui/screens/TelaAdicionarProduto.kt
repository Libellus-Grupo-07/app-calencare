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
import school.sptech.R
import school.sptech.data.model.CategoriaProduto
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.Background
import school.sptech.ui.components.DropdownFieldWithLabel
import school.sptech.ui.components.FormButtons
import school.sptech.ui.components.FormFieldWithLabel
import school.sptech.ui.components.LabelInput
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.CinzaOpacidade35
import school.sptech.ui.theme.CinzaOpacidade7
import school.sptech.ui.theme.Preto
import school.sptech.ui.viewModel.ProdutoViewModel

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
    viewModel.getCategoriasProduto()
    var deuRuim by remember { mutableStateOf(viewModel.deuErro) }
    var msg by remember { mutableStateOf(viewModel.erro) }

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
                Spacer(modifier = Modifier.height(4.dp))
                ProductForm(viewModel = viewModel)
                FormButtons(
                    onAddClick = {
                        viewModel.adicionarProduto()
                        deuRuim = viewModel.deuErro
                        msg = viewModel.erro

                        if (!deuRuim) {
                            navController.popBackStack()
                        }
                    },
                    onCancelClick = { navController.popBackStack() }
                )
            }
        }
    }

    if (deuRuim) {
        AlertError(msg = msg)
    }
}

@Composable
fun ProductForm(viewModel: ProdutoViewModel) {
    Column(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(16.dp)) {
        FormFieldWithLabel(
            value = viewModel.produto.nome ?: "",
            onValueChange = { viewModel.produto = viewModel.produto.copy(nome = it) },
            label = stringResource(R.string.nome)
        )

        FormFieldWithLabel(
            value = viewModel.produto.marca ?: "",
            onValueChange = { viewModel.produto = viewModel.produto.copy(marca = it) },
            label = stringResource(R.string.marca)
        )

        DropdownFieldWithLabel(
            value = viewModel.categoriaProduto.nome ?: "",
            onValueChange = {
                viewModel.categoriaProduto = viewModel.categoriaProduto.copy(nome = it)
            },
            label = stringResource(R.string.categoria),
            options = viewModel.categoriasProduto.map { it.nome ?: "" }
        )

        FormFieldWithLabel(
            value = viewModel.produto.descricao ?: "",
            onValueChange = { viewModel.produto = viewModel.produto.copy(descricao = it) },
            label = stringResource(R.string.descricao),
            isMultiline = true
        )

        Row(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(8.dp)) {
            LabelInput(label = stringResource(R.string.possui_validade))
            Checkbox(
                modifier = Modifier.size(24.dp),
                checked = viewModel.validade.enabledValidade ?: false,
                onCheckedChange = {
                    viewModel.validade = viewModel.validade.copy(enabledValidade = it)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Preto,
                    uncheckedColor = Cinza,
                    checkmarkColor = Branco,
                    disabledCheckedColor = CinzaOpacidade35,
                    disabledUncheckedColor = CinzaOpacidade7,
                    disabledIndeterminateColor = CinzaOpacidade7
                )
            )
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

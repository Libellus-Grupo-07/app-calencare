package school.sptech.ui.screens

import FilterModal
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import school.sptech.R
import school.sptech.data.model.CategoriaProduto
import school.sptech.data.model.Produto
import school.sptech.preferencesHelper
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.AlertSuccess
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxProdutos
import school.sptech.ui.components.TopBarEstoque
import school.sptech.ui.viewModel.ProdutoViewModel
import school.sptech.ui.viewModel.ValidadeViewModel

class Estoque : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaEstoque(navController = rememberNavController())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaEstoque(
    produtoViewModel: ProdutoViewModel = viewModel(),
    validadeViewModel: ValidadeViewModel = viewModel(),
    navController: NavController
) {
    LaunchedEffect("produtos") {
        produtoViewModel.getProdutos(preferencesHelper.getIdEmpresa())
        produtoViewModel.getCategoriasProduto()
    }

    var listaProdutos = remember {
        mutableStateListOf<Produto>().apply {
            addAll(produtoViewModel.getListaProdutos())
        }
    }

    var exibirFiltro by remember {
        mutableStateOf(false)
    }

    Background()

    Column(modifier = Modifier.padding(vertical = 24.dp)) {
        TopBarEstoque(
            navController = navController,
            onClickFiltro = { exibirFiltro = !exibirFiltro }
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            BoxProdutos(
                produtos = listaProdutos.let {
                    if (it.isEmpty() && produtoViewModel.filtroIsEmpty()) {
                        it.addAll(produtoViewModel.getListaProdutos())
                    }
                    it
                },
                titulo = stringResource(id = R.string.estoque),
                isTelaInicio = false,
                modifier = Modifier.fillMaxWidth(),
                validadeViewModel = validadeViewModel,
                navController = navController,
            )
        }
    }

    if (produtoViewModel.deuErro || validadeViewModel.deuErro) {
        AlertError(
            msg = "Ops! Algo deu errado. Tente novamente mais tarde.",
            onClick = {
                produtoViewModel.deuErro = false
                validadeViewModel.deuErro = false
            }
        )

        LaunchedEffect("erro") {
            delay(6000)
            produtoViewModel.deuErro = false
            validadeViewModel.deuErro = false
            validadeViewModel.erro = ""
        }
    }

    if (!validadeViewModel.deuErro && validadeViewModel.erro.isNotEmpty()) {
        AlertSuccess(
            msg = "Estoque atualizado com sucesso!",
            onClick = { validadeViewModel.erro = "" }
        )

        LaunchedEffect("sucess") {
            validadeViewModel.atualizarQtdEstoqueValidades()
            produtoViewModel.getProdutos(preferencesHelper.getIdEmpresa())
            delay(6000)
            validadeViewModel.erro = ""
        }
    }

    if (exibirFiltro) {
        FilterModal(
            produtoViewModel = produtoViewModel,
            onDismiss = { exibirFiltro = false },
            onLimpar = {
                produtoViewModel.limparFiltro()
                listaProdutos.clear()
                listaProdutos.addAll(produtoViewModel.getListaProdutos())
                exibirFiltro = false
            },
            onSalvar = {
                listaProdutos.clear()
                listaProdutos.addAll(produtoViewModel.getListaProdutosPorFiltro())
                exibirFiltro = false
            }

        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EstoquePreview() {
    TelaEstoque(navController = rememberNavController())
}
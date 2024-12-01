package school.sptech.ui.screens

import FiltroEstoqueModal
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import school.sptech.Routes
import school.sptech.dataStoreRepository
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.AlertSuccess
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxProdutos
import school.sptech.ui.components.TopBarSearch
import school.sptech.ui.viewModel.ProdutoViewModel
import school.sptech.ui.viewModel.ValidadeViewModel

class TelaEstoque : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaEstoqueScreen(navController = rememberNavController())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaEstoqueScreen(
    produtoViewModel: ProdutoViewModel = koinViewModel(),
    validadeViewModel: ValidadeViewModel = koinViewModel(),
    navController: NavController
) {
    LaunchedEffect("produtos") {
        val idEmpresa = dataStoreRepository.getEmpresaId()

        produtoViewModel.getProdutos(idEmpresa)
        produtoViewModel.getCategoriasProduto()
    }

    var exibirFiltro by remember {
        mutableStateOf(false)
    }


    Background()

    Column(modifier = Modifier.padding(vertical = 24.dp)) {
        TopBarSearch(
            onClickBack = { navController.popBackStack() },
            onClickAdd = {
                navController.navigate(Routes.AdicionarProduto.route)
            },
            onClickFiltro = { exibirFiltro = !exibirFiltro }
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            BoxProdutos(
                produtos =
                if (produtoViewModel.filtroIsEmpty()) produtoViewModel.getListaProdutos()
                else produtoViewModel.getListaProdutosPorFiltro(),
                titulo = stringResource(id = R.string.estoque),
                isTelaInicio = false,
                modifier = Modifier.fillMaxWidth(),
                validadeViewModel = validadeViewModel,
                navController = navController,
            )

            if (produtoViewModel.deuErro || validadeViewModel.deuErro) {
                AlertError(
                    msg = produtoViewModel.mensagem,
                    onClick = {
                        produtoViewModel.deuErro = false
                        produtoViewModel.mensagem = ""
                        validadeViewModel.deuErro = false
                        validadeViewModel.erro = ""
                    }
                )

                LaunchedEffect("erro") {
                    delay(6000)
                    produtoViewModel.deuErro = false
                    validadeViewModel.deuErro = false
                    produtoViewModel.mensagem = ""
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
                    produtoViewModel.getProdutos(dataStoreRepository.getEmpresaId())
                    delay(6000)
                    validadeViewModel.erro = ""
                }
            }
        }
    }



    if (exibirFiltro) {
        produtoViewModel.atualizarValidadeProdutos(produtoViewModel.getListaProdutos())

        FiltroEstoqueModal(
            produtoViewModel = produtoViewModel,
            onDismiss = { exibirFiltro = false },
            onLimpar = {
                produtoViewModel.limparFiltro()
                exibirFiltro = false
            },
            onSalvar = {
                exibirFiltro = false
            },
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EstoquePreview() {
    TelaEstoqueScreen(navController = rememberNavController())
}
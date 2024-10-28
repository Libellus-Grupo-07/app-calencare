package school.sptech.ui.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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

@Composable
fun TelaEstoque(
    produtoViewModel: ProdutoViewModel = viewModel(),
    validadeViewModel: ValidadeViewModel = viewModel(),
    navController: NavController
) {
    LaunchedEffect("produtos") {
        produtoViewModel.getProdutos(preferencesHelper.getIdEmpresa())
    }

    val listaProdutos = produtoViewModel.getListaProdutos()

    Background()

    Column(modifier = Modifier.padding(vertical = 24.dp)) {
        TopBarEstoque(navController = navController)
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            BoxProdutos(
                produtos = listaProdutos,
                titulo = stringResource(id = R.string.estoque),
                isTelaInicio = false,
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
            )
        }
    }

    if(produtoViewModel.deuErro) {
        AlertError(msg = "Ops! Algo deu errado. Tente novamente mais tarde.")

        LaunchedEffect("erro") {
            delay(6000)
            produtoViewModel.deuErro = false
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EstoquePreview() {
    TelaEstoque(navController = rememberNavController())
}
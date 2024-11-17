package school.sptech.ui.screens

import FiltroDespesaModal
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import school.sptech.Routes
import school.sptech.data.model.Despesa
import school.sptech.preferencesHelper
import school.sptech.ui.components.Background
import school.sptech.ui.components.CardDespesa
import school.sptech.ui.components.TextoNenhumItemCadastrado
import school.sptech.ui.components.TituloLarge
import school.sptech.ui.components.TopBarSearch
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.viewModel.DespesaViewModel

class TelaDespesas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TelaDespesasScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TelaDespesasScreen(
    navController: NavController = rememberNavController(),
    despesaViewModel: DespesaViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val idEmpresa = preferencesHelper.getIdEmpresa()
    var exibirFiltro by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        despesaViewModel.getDespesas(
            empresaId = idEmpresa,
        )
        despesaViewModel.getCategoriasDespesa()
    }

    var despesas = remember {
        mutableListOf<Despesa>().apply {
            addAll(despesaViewModel.getDespesas(idEmpresa))
        }
    }

    Background()
    Column(modifier = modifier.padding(vertical = 24.dp))
    {
        TopBarSearch(
            onClickBack = { navController.popBackStack() },
            onClickAdd = { navController.navigate(Routes.AdicionarDespesa.route) },
            onClickFiltro = { exibirFiltro = true },
        )

        if (despesaViewModel.filtroIsEmpty() && despesas.isEmpty()) {
            despesas.addAll(despesaViewModel.getListaDespesas())
        }

        ListaDespesas(
            despesas = despesas,
        )
    }


    if (exibirFiltro) {
        FiltroDespesaModal(
            onSalvar = {
                despesas.clear()
                despesas.addAll(despesaViewModel.filtrarDespesas())
                exibirFiltro = false
            },
            onLimpar = {
                despesaViewModel.limparFiltro()
                despesas.clear()
                despesas.addAll(despesaViewModel.getListaDespesas())
                exibirFiltro = false
            },
            onDismiss = { exibirFiltro = false },
            despesaViewModel = despesaViewModel
        )
    }
}


@Composable
fun ListaDespesas(despesas: List<Despesa>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TituloLarge(titulo = "Despesas")

        if (despesas.isEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextoNenhumItemCadastrado(texto = "Nenhuma despesa encontrada")
            }
        } else {
            LazyColumn {
                items(despesas) { despesa ->
                    CardDespesa(despesa)
                }
            }
        }


    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaDespesasPreview() {
    CalencareAppTheme {
        TelaDespesas()
    }
}
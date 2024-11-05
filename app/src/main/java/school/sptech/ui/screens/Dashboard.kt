package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import getMonthInt
import kotlinx.coroutines.delay
import school.sptech.R
import school.sptech.preferencesHelper
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxMovimentos
import school.sptech.ui.components.Chart
import school.sptech.ui.components.SeletorData
import school.sptech.ui.components.TopBarComSelecaoData
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Preto
import school.sptech.ui.viewModel.FinancasViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class Dashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TelaDashboard(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TelaDashboard(
    financasViewModel: FinancasViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
//    val despesas = remember { mutableStateListOf<Despesa>() }
//    val receitas = remember { mutableStateOf(3669.91) }
//    val despesasTotais = remember { mutableStateOf(11080.00) }
//    val faturamento = remember { mutableStateOf(14749.91) }
//    val comissoes = remember { mutableStateOf(8000.00) }

    var mesSelecionado by remember {
        mutableStateOf(
            LocalDate.now().month.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            ).replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        )
    }

    var anoSelecionado by remember { mutableStateOf(LocalDate.now().year) }
    var mostrarSeletorData by remember { mutableStateOf(false) }
    val idEmpresa = preferencesHelper.getIdEmpresa()
    var movimentos = financasViewModel.movimentos

    LaunchedEffect("getMovimentosDoMes") {
        financasViewModel.getMovimentosDoMes(
            empresaId = idEmpresa,
            mes = getMonthInt(mesSelecionado)?.value ?: 0,
            ano = anoSelecionado
        )
    }

    if (mostrarSeletorData) {
        SeletorData(
            mesSelecionado = mesSelecionado,
            anoSelecionado = anoSelecionado,
            onDismissRequest = { mostrarSeletorData = false },
            onConfirm = { mes, ano ->
                mesSelecionado = mes
                anoSelecionado = ano
                mostrarSeletorData = false

                financasViewModel.getMovimentosDoMes(
                    empresaId = idEmpresa,
                    mes = getMonthInt(mesSelecionado)?.value ?: 0,
                    ano = anoSelecionado
                )
            }
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Background()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 2.dp)
        ) {
            TopBarComSelecaoData(
                titulo = stringResource(id = R.string.dashboard),
                mesSelecionado = mesSelecionado,
                anoSelecionado = anoSelecionado,
                aoClicarSeletorData = { mostrarSeletorData = true },
            )
            Chart()

            BoxMovimentos(
                movimentos = movimentos
            )
        }
    }

    if(financasViewModel.deuErro){
        AlertError(msg = financasViewModel.erro)

        LaunchedEffect("error") {
            delay(5000)
            financasViewModel.deuErro = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaDashboardPreview() {
    CalencareAppTheme {
        TelaDashboard()
    }
}
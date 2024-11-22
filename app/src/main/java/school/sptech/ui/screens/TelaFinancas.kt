package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import formatarDecimal
import getMonthInt
import kotlinx.coroutines.delay
import school.sptech.R
import school.sptech.Routes
import school.sptech.data.model.Movimentacoes
import school.sptech.preferencesHelper
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxMovimentacoes
import school.sptech.ui.components.CardKpi
import school.sptech.ui.components.Chart
import school.sptech.ui.components.SeletorData
import school.sptech.ui.components.TopBarComSelecaoData
import school.sptech.ui.theme.*
import school.sptech.ui.viewModel.DespesaViewModel
import school.sptech.ui.viewModel.MovimentacoesViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class TelaFinancas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                TelaFinancasScreen()
            }
        }
    }
}

@Composable
fun TelaFinancasScreen(
    financasViewModel: MovimentacoesViewModel = viewModel(),
    despesaViewModel: DespesaViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {

    val receitas = remember { mutableStateOf(0.0) }
    val faturamento = remember { mutableStateOf(0.0) }
    val comissoes = remember { mutableStateOf(0.0) }
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

    LaunchedEffect(Unit) {
        financasViewModel.getMovimentacoes(
            empresaId = idEmpresa,
            ano = anoSelecionado,
            mes = getMonthInt(mesSelecionado)?.value ?: 0
        )

        despesaViewModel.getTotalDespesasMes(
            ano = anoSelecionado,
            mes = getMonthInt(mesSelecionado)?.value ?: 0
        )
    }

    var despesasTotais = despesaViewModel.totalDespesas

    if (mostrarSeletorData) {
        SeletorData(
            mesSelecionado = mesSelecionado,
            anoSelecionado = anoSelecionado,
            onDismissRequest = { mostrarSeletorData = false },
            onConfirm = { mes, ano ->
                mesSelecionado = mes
                anoSelecionado = ano
                mostrarSeletorData = false

                financasViewModel.getMovimentacoes(
                    empresaId = idEmpresa,
                    ano = anoSelecionado,
                    mes = getMonthInt(mesSelecionado)?.value ?: 0
                )

                despesaViewModel.getTotalDespesasMes(
                    ano = anoSelecionado,
                    mes = getMonthInt(mesSelecionado)?.value ?: 0
                )

            }
        )
    }

    ConteudoTelaFinancas(
        mesSelecionado = mesSelecionado,
        anoSelecionado = anoSelecionado,
        aoClicarSeletorData = { mostrarSeletorData = true },
        receitas = receitas.value,
        despesasTotais = despesasTotais,
        faturamento = faturamento.value,
        comissoes = comissoes.value,
        movimentacoes = financasViewModel.movimentacoes,
        navController = navController
    )

    if (despesaViewModel.deuErro) {
        AlertError(msg = despesaViewModel.erro)

        LaunchedEffect("error") {
            delay(6000)
            despesaViewModel.deuErro = false
        }
    }
}

@Composable
fun ConteudoTelaFinancas(
    mesSelecionado: String,
    anoSelecionado: Int,
    aoClicarSeletorData: () -> Unit,
    receitas: Double,
    despesasTotais: Double,
    faturamento: Double,
    comissoes: Double,
    movimentacoes: List<Movimentacoes>,
    navController: NavController
) {
    Background()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            TopBarComSelecaoData(
                stringResource(id = R.string.financas),
                mesSelecionado,
                anoSelecionado,
                aoClicarSeletorData
            )

            ResumoFinanceiro(receitas, despesasTotais, faturamento, comissoes)
        }

        Chart()

        BoxMovimentacoes(movimentacoes = movimentacoes, onClickCard = {
            navController.navigate(Routes.InformacoesMovimentacao.route)
        })

    }
}

@Composable
fun ResumoFinanceiro(
    receitas: Double,
    despesas: Double,
    lucro: Double,
    comissoes: Double,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(0.5f)) {
                CardKpi(
                    "Total em Receitas",
                    "R$ + ${formatarDecimal(receitas.toFloat())}",
                    "AZUL"
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(0.5f)) {
                CardKpi(
                    "Total em Despesas",
                    "R$ - ${formatarDecimal(despesas.toFloat())}",
                    "VERMELHO"
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(0.4f)) {
                CardKpi(
                    "Total em Lucro",
                    "R$ + ${formatarDecimal(lucro.toFloat())}",
                    "VERDE"
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(0.4f)) {
                CardKpi(
                    "Total em Comissões",
                    "R$ - ${formatarDecimal(comissoes.toFloat())}",
                    "LARANJA"
                )
            }

        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviaTelaFinancas() {
    TelaFinancasScreen()
}
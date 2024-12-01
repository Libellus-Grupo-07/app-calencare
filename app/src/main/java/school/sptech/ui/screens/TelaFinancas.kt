package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import getMonthString
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import school.sptech.R
import school.sptech.Routes
import school.sptech.data.model.Movimentacoes
import school.sptech.dataStoreRepository
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxMovimentacoes
import school.sptech.ui.components.CardKpi
import school.sptech.ui.components.Chart
import school.sptech.ui.components.SeletorData
import school.sptech.ui.components.TopBarComSelecaoData
import school.sptech.ui.theme.*
import school.sptech.ui.viewModel.DashFinancasViewModel
import school.sptech.ui.viewModel.DespesaViewModel
import school.sptech.ui.viewModel.FinancasViewModel
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
    dashFinancasViewModel: DashFinancasViewModel = koinViewModel(),
    financasViewModel: FinancasViewModel = koinViewModel(),
    navController: NavController = rememberNavController()
) {
    var mesSelecionado by remember { mutableStateOf(financasViewModel.mes)}
    var anoSelecionado by remember { mutableStateOf(financasViewModel.ano)}

    var mostrarSeletorData by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        dashFinancasViewModel.getDadosDashPorMesAno(
            ano = anoSelecionado,
            mes = mesSelecionado
        )

        financasViewModel.getMovimentacoes(
            ano = anoSelecionado,
            mes =mesSelecionado
        )

        financasViewModel.getKpisFinancas(
            ano = anoSelecionado,
            mes = mesSelecionado
        )
    }

    var receitasTotais = financasViewModel.totalReceitas
    var despesasTotais = financasViewModel.totalDespesas
    var lucrosTotais = financasViewModel.totalLucro
    var comissoesTotais = financasViewModel.totalComissoes

    if (mostrarSeletorData) {
        SeletorData(
            mesSelecionado = mesSelecionado,
            anoSelecionado = anoSelecionado,
            onDismissRequest = { mostrarSeletorData = false },
            onConfirm = { mes, ano ->
                mesSelecionado = mes
                anoSelecionado = ano
                mostrarSeletorData = false

                dashFinancasViewModel.getDadosDashPorMesAno(
                    ano = anoSelecionado,
                    mes = mesSelecionado
                )

                financasViewModel.getMovimentacoes(
                    ano = anoSelecionado,
                    mes = mesSelecionado
                )

                financasViewModel.getKpisFinancas(
                    ano = anoSelecionado,
                    mes = mesSelecionado
                )

            }
        )
    }

    ConteudoTelaFinancas(
        mesSelecionado = mesSelecionado,
        anoSelecionado = anoSelecionado,
        aoClicarSeletorData = { mostrarSeletorData = true },
        receitas = receitasTotais,
        despesasTotais = despesasTotais,
        lucro = lucrosTotais,
        comissoes = comissoesTotais,
        movimentacoes = financasViewModel.movimentacoes,
        viewModel = dashFinancasViewModel,
        navController = navController
    )

    if (financasViewModel.deuErro) {
        AlertError(msg = financasViewModel.erro)

        LaunchedEffect("error") {
            delay(6000)
            financasViewModel.deuErro = false
        }
    }
}

@Composable
fun ConteudoTelaFinancas(
    mesSelecionado: Int,
    anoSelecionado: Int,
    aoClicarSeletorData: () -> Unit,
    receitas: Double,
    despesasTotais: Double,
    lucro: Double,
    comissoes: Double,
    movimentacoes: List<Movimentacoes>,
    viewModel: DashFinancasViewModel,
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
                getMonthString(mesSelecionado),
                anoSelecionado,
                aoClicarSeletorData
            )

            ResumoFinanceiro(receitas, despesasTotais, lucro, comissoes)
        }

        Chart(
            labels = viewModel.labels,
            receitas = viewModel.receitas.map { it.valor ?: 0.0 }.toList(),
            lucro = viewModel.lucros.map { it.valor ?: 0.0 }.toList(),
            despesas = viewModel.despesas.map { it.valor ?: 0.0 }.toList()
        )

        BoxMovimentacoes(movimentacoes = movimentacoes, onClickCard = {
            navController.navigate("${Routes.InformacoesMovimentacao.route}/${it.data}/${it.descricao}/${it.total}")
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
                    "+ R$ ${formatarDecimal(receitas.toFloat())}",
                    "AZUL"
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(0.5f)) {
                CardKpi(
                    "Total em Despesas",
                    "- R$ ${formatarDecimal(despesas.toFloat())}",
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
                    "+ R$ ${formatarDecimal(lucro.toFloat())}",
                    "VERDE"
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(0.4f)) {
                CardKpi(
                    "Total em Comissões",
                    "- R$ ${formatarDecimal(comissoes.toFloat())}",
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
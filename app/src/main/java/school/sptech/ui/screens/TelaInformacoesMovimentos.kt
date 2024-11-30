package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import school.sptech.data.model.Agendamento
import school.sptech.data.model.Despesa
import school.sptech.data.model.Movimentacoes
import school.sptech.dataStoreRepository
import school.sptech.ui.components.Background
import school.sptech.ui.components.CardAgendamentoMovimentacao
import school.sptech.ui.components.CardComissaoMovimentacao
import school.sptech.ui.components.CardDespesaMovimentacao
import school.sptech.ui.components.CardMovimentacoes
import school.sptech.ui.components.LabelInput
import school.sptech.ui.components.TituloLarge
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.viewModel.AgendamentoViewModel
import school.sptech.ui.viewModel.DespesaViewModel
import transformarEmLocalDateTime
import java.time.LocalDate

class TelaInformacoesMovimentos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TelaInformacoesMovimentosScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TelaInformacoesMovimentosScreen(
    modifier: Modifier = Modifier,
    agendamentoViewModel: AgendamentoViewModel = viewModel(),
    despesaViewModel: DespesaViewModel = viewModel(),
    navController: NavController = rememberNavController(),
    data: String = LocalDate.now().toString(),
    tipo: String = "",
    valor: String = "",
) {
    val movimentos by remember {
        mutableStateOf(
            Movimentacoes(
                total = valor,
                data = data,
                descricao = tipo
            )
        )
    }

    LaunchedEffect("movimentos") {
        when (tipo) {
            "Despesas" -> {
                despesaViewModel.data = data
                despesaViewModel.getDespesasPorData(dataStoreRepository.getEmpresaId())
            }

            else -> {
                agendamentoViewModel.getAgendamentosPorData(
                    dataInicio = transformarEmLocalDateTime(
                        data,
                        isDateBd = true
                    ).toString(),
                    dataFim = transformarEmLocalDateTime(
                        data,
                        isDateBd = true,
                        isFinalDay = true
                    ).toString()
                )
            }
        }
    }

    Background()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarVoltar(navController = navController, titulo = "")

        Column(
            modifier = modifier.fillMaxWidth(0.85f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CardMovimentacoes(
                movimentacoes = movimentos,
                onClick = { /*TODO*/ },
                isClickableCard = false,
                isLargeCard = true,
            )

            TituloLarge(titulo = "Relatório do Dia")

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 28.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (tipo) {
                    "Despesas" -> {
                        items(despesaViewModel.getListaDespesas().size) { i ->
                            CardDespesaMovimentacao(despesa = despesaViewModel.getListaDespesas()[i])
                        }
                    }

                    else -> {
                        items(agendamentoViewModel.getListaAgendamentos().size) { i ->
                            CardAgendamentoMovimentacao(agendamento = agendamentoViewModel.getListaAgendamentos()[i])
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaInformacoesMovimentosPreview() {
    CalencareAppTheme {
        TelaInformacoesMovimentosScreen()
    }
}
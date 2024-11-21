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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import school.sptech.data.model.Agendamento
import school.sptech.data.model.Despesa
import school.sptech.data.model.Movimentacoes
import school.sptech.ui.components.Background
import school.sptech.ui.components.CardAgendamentoMovimentacao
import school.sptech.ui.components.CardComissaoMovimentacao
import school.sptech.ui.components.CardDespesaMovimentacao
import school.sptech.ui.components.CardMovimentacoes
import school.sptech.ui.components.TituloLarge
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.theme.CalencareAppTheme

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
    navController: NavController = rememberNavController()
) {
    val movimentos by remember {
        mutableStateOf(
            Movimentacoes(
                total = "1233.0",
                data = "2024-12-12",
                descricao = "Comissões"
            )
        )
    }

    val despesas = listOf(
        Despesa(
            id = 1,
            nome = "Despesa 1",
            observacao = "Observação 1",
            valor = "123.0",
            formaPagamento = "Dinheiro",
            dtPagamento = "2024-12-12",
            dtCriacao = "2024-12-12",
            categoriaDespesaNome = "Categoria 1",
            categoriaDespesaId = 1,
            empresaId = 1
        ),
        Despesa(
            id = 2,
            nome = "Despesa 2",
            observacao = "Observação 2",
            valor = "123.0",
            formaPagamento = "Dinheiro",
            dtPagamento = "2024-12-12",
            dtCriacao = "2024-12-12",
            categoriaDespesaNome = "Categoria 2",
            categoriaDespesaId = 2,
            empresaId = 2
        ),
    )

    val agendamentos = listOf(
        Agendamento(
            dia = "2024-12-12",
            horario = "12:00",
            horarioFinalizacao = "13:00",
            nomeServico = "Unha de Gel",
            nomeCliente = "Maria dos Santos",
            nomeFuncionario = "João da Silva",
            preco = 123.0,
        ),
        Agendamento(
            dia = "2024-12-12",
            horario = "12:00",
            horarioFinalizacao = "13:00",
            nomeServico = "Unha de Gel",
            nomeCliente = "Maria dos Santos",
            nomeFuncionario = "João da Silva",
            preco = 123.0,
        ),
    )

    val comissoes = listOf(
        Movimentacoes(
            total = "1233.0",
            data = "2024-12-12",
            descricao = "Comissões"
        ),
        Movimentacoes(
            total = "1233.0",
            data = "2024-12-12",
            descricao = "Comissões"
        ),
    )

    Background()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
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
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (movimentos.descricao) {
                    "Despesas" -> {
                        items(despesas.size) { i ->
                            CardDespesaMovimentacao(despesa = despesas[i])
                        }
                    }

                    "Agendamentos" -> {
                        items(agendamentos.size) { i ->
                            CardAgendamentoMovimentacao(agendamento = agendamentos[i])
                        }
                    }

                    else -> {
                        items(comissoes.size) { i ->
                            CardComissaoMovimentacao(
                                comissao = comissoes[i],
                            )
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
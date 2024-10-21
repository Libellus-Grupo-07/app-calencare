package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import formatarDecimal
import school.sptech.R
import school.sptech.Routes
import school.sptech.data.model.CategoriaDespesa
import school.sptech.data.model.Despesa
import school.sptech.ui.components.Background
import school.sptech.ui.components.CardDespesa
import school.sptech.ui.components.CardKpi
import school.sptech.ui.components.SeletorData
import school.sptech.ui.components.TextoButtonMedium
import school.sptech.ui.components.TituloLarge
import school.sptech.ui.components.TopBarComSelecaoData
import school.sptech.ui.theme.*
import java.time.LocalDate

class TelaFinancasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                TelaFinancas()
            }
        }
    }
}

@Composable
fun TelaFinancas(navController: NavController = rememberNavController()) {
    val despesas = remember { mutableStateListOf<Despesa>() }
    val receitas = remember { mutableStateOf(3669.91) }
    val despesasTotais = remember { mutableStateOf(11080.00) }
    val faturamento = remember { mutableStateOf(14749.91) }
    val comissoes = remember { mutableStateOf(8000.00) }
    var mesSelecionado by remember { mutableStateOf("Setembro") }
    var anoSelecionado by remember { mutableStateOf(2024) }
    var mostrarSeletorData by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        despesas.addAll(
            listOf(
                Despesa(
                    nome = "Despesa 1",
                    valor = "R$ 150,00",
                    dtCriacao = LocalDate.parse("2024-09-01"),
                    categoriaDespesa = CategoriaDespesa(nome = "Categoria A")
                ),
                Despesa(
                    nome = "Despesa 2",
                    valor = "R$ 300,00",
                    dtCriacao = LocalDate.parse("2024-09-15"),
                    categoriaDespesa = CategoriaDespesa(nome = "Categoria B")
                )
            )
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
            }
        )
    }

    ConteudoTelaFinancas(
        mesSelecionado = mesSelecionado,
        anoSelecionado = anoSelecionado,
        aoClicarSeletorData = { mostrarSeletorData = true },
        receitas = receitas.value,
        despesasTotais = despesasTotais.value,
        faturamento = faturamento.value,
        comissoes = comissoes.value,
        despesas = despesas,
        corTitulo = Preto,
        adicionarDespesa = { navController.navigate(Routes.AdicionarDespesa.route) }
    )
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
    despesas: List<Despesa>,
    corTitulo: Color,
    adicionarDespesa: (Despesa) -> Unit
) {
    Background()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .padding(top = 16.dp)
    ) {
        TopBarComSelecaoData(
            stringResource(id = R.string.financas),
            mesSelecionado,
            anoSelecionado,
            aoClicarSeletorData
        )
        ResumoFinanceiro(receitas, despesasTotais, faturamento, comissoes)
        Spacer(modifier = Modifier.height(16.dp))
        ListaDespesas(despesas, corTitulo, adicionarDespesa)
    }
}

@Composable
fun ResumoFinanceiro(
    receitas: Double,
    despesas: Double,
    faturamento: Double,
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
                CardKpi("Total em Receitas", "R$ + ${formatarDecimal(receitas.toFloat())}", "VERDE")
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
                    "Total em Faturamento",
                    "R$ + ${formatarDecimal(faturamento.toFloat())}",
                    "AZUL"
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

@Composable
fun ListaDespesas(despesas: List<Despesa>, corTexto: Color, adicionarDespesa: (Despesa) -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TituloLarge(titulo = "Despesas")
            TextButton(
                onClick = {
                    adicionarDespesa(
                        Despesa(
                            nome ="Nova Despesa",
                            valor = "R$ 100,00",
                            dtCriacao = LocalDate.parse("2024-10-01"),
                            categoriaDespesa = CategoriaDespesa(nome = "Categoria C")
                        )
                    )
                },
                colors = ButtonColors(
                    contentColor = Cinza,
                    containerColor = Color.Transparent,
                    disabledContentColor = Cinza,
                    disabledContainerColor = Color.Transparent
                )
            ) {
                TextoButtonMedium(texto = "Adicionar Despesa")
            }
        }

        LazyColumn {
            items(despesas) { despesa ->
                CardDespesa(despesa, corTexto)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviaTelaFinancas() {
    TelaFinancas()
}
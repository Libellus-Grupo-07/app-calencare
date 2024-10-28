package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
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
import school.sptech.data.model.Despesa
import school.sptech.preferencesHelper
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.Background
import school.sptech.ui.components.CardDespesa
import school.sptech.ui.components.CardKpi
import school.sptech.ui.components.SeletorData
import school.sptech.ui.components.TextoButtonMedium
import school.sptech.ui.components.TituloLarge
import school.sptech.ui.components.TopBarComSelecaoData
import school.sptech.ui.theme.*
import school.sptech.ui.viewModel.DespesaViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

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
fun TelaFinancas(
    despesaViewModel: DespesaViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {

    val receitas = remember { mutableStateOf(3669.91) }
    val despesasTotais = remember { mutableStateOf(11080.00) }
    val faturamento = remember { mutableStateOf(14749.91) }
    val comissoes = remember { mutableStateOf(8000.00) }
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

    var deuErro by remember { mutableStateOf(false) }
    var erro by remember { mutableStateOf("") }
    var despesas = despesaViewModel.listaDespesas

    LaunchedEffect(Unit) {
        despesaViewModel.getDespesas(
            empresaId = idEmpresa,
            ano = anoSelecionado,
            mes = getMonthInt(mesSelecionado)?.value ?: 0
        )
    }

    deuErro = despesaViewModel.deuErro
    erro = despesaViewModel.erro


    if (mostrarSeletorData) {
        SeletorData(
            mesSelecionado = mesSelecionado,
            anoSelecionado = anoSelecionado,
            onDismissRequest = { mostrarSeletorData = false },
            onConfirm = { mes, ano ->
                mesSelecionado = mes
                anoSelecionado = ano
                mostrarSeletorData = false

                despesaViewModel.getDespesas(
                    empresaId = idEmpresa,
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
        despesasTotais = despesasTotais.value,
        faturamento = faturamento.value,
        comissoes = comissoes.value,
        despesas = despesas,
        corTitulo = Preto,
        adicionarDespesa = { navController.navigate(Routes.AdicionarDespesa.route) }
    )

    if(despesaViewModel.deuErro){
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
                    "R$ + ${formatarDecimal(faturamento.toFloat())}",
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
                            nome = "Nova Despesa",
                            valor = "100.00",
                            dtCriacao = "2024-10-01",
                            categoriaDespesaNome = "Categoria C"
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
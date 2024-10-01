package school.sptech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.ui.theme.*

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
fun TelaFinancas() {
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
                    name = "Despesa 1",
                    value = "R$ 150,00",
                    date = "01/09/2024",
                    category = "Categoria A"
                ),
                Despesa(
                    name = "Despesa 2",
                    value = "R$ 300,00",
                    date = "15/09/2024",
                    category = "Categoria B"
                )
            )
        )
    }

    ConteudoTelaFinancas(
        mesSelecionado = mesSelecionado,
        anoSelecionado = anoSelecionado,
        mostrarSeletorData = mostrarSeletorData,
        aoClicarSeletorData = { mostrarSeletorData = true },
        aoSolicitarFechamento = { mostrarSeletorData = false },
        aoSelecionarData = { mes, ano ->
            mesSelecionado = mes
            anoSelecionado = ano
            mostrarSeletorData = false
        },
        receitas = receitas.value,
        despesasTotais = despesasTotais.value,
        faturamento = faturamento.value,
        comissoes = comissoes.value,
        despesas = despesas,
        corTitulo = Preto,
        corValor = Verde,
        adicionarDespesa = { despesa ->
            despesas.add(despesa)
        }
    )
}

data class Despesa(
    val name: String,
    val value: String,
    val date: String,
    val category: String
)

@Composable
fun ConteudoTelaFinancas(
    mesSelecionado: String,
    anoSelecionado: Int,
    mostrarSeletorData: Boolean,
    aoClicarSeletorData: () -> Unit,
    aoSolicitarFechamento: () -> Unit,
    aoSelecionarData: (String, Int) -> Unit,
    receitas: Double,
    despesasTotais: Double,
    faturamento: Double,
    comissoes: Double,
    despesas: List<Despesa>,
    corTitulo: Color,
    corValor: Color,
    adicionarDespesa: (Despesa) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BrancoFundo)
            .padding(16.dp)
    ) {
        Cabecalho(
            mesSelecionado = mesSelecionado,
            anoSelecionado = anoSelecionado,
            aoClicarSeletorData = aoClicarSeletorData,
            corTexto = corTitulo
        )

        ResumoFinanceiro(receitas, despesasTotais, faturamento, comissoes, corValor)

        Spacer(modifier = Modifier.height(16.dp))
        ListaDespesas(despesas = despesas, corTexto = corTitulo, adicionarDespesa = adicionarDespesa)
    }
}

@Composable
fun Cabecalho(
    mesSelecionado: String,
    anoSelecionado: Int,
    aoClicarSeletorData: () -> Unit,
    corTexto: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Finanças",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = RoxoNubank
        )
        TextButton(
            onClick = aoClicarSeletorData
        ) {
            Text(
                text = "$mesSelecionado $anoSelecionado",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Preto
            )
        }
    }
}

@Composable
fun ResumoFinanceiro(
    receitas: Double,
    despesas: Double,
    faturamento: Double,
    comissoes: Double,
    corValor: Color
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CartaoResumo(
                titulo = "Total em Receitas",
                valor = "R$ +${receitas}",
                corFundo = VerdeOpacidade15,
                corValor = Verde
            )
            CartaoResumo(
                titulo = "Total em Despesas",
                valor = "R$ -${despesas}",
                corFundo = VermelhoOpacidade15,
                corValor = Vermelho
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CartaoResumo(
                titulo = "Total em Faturamento",
                valor = "R$ +${faturamento}",
                corFundo = AzulOpacidade15,
                corValor = Azul
            )
            CartaoResumo(
                titulo = "Total em Comissões",
                valor = "R$ -${comissoes}",
                corFundo = AmareloOpacidade10,
                corValor = Amarelo
            )
        }
    }
}

@Composable
fun CartaoResumo(titulo: String, valor: String, corFundo: Color, corValor: Color) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .height(100.dp)
            .background(corFundo, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = titulo,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = Preto,
            )
            Text(
                text = valor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
                color = corValor,
                textAlign = TextAlign.Center
            )
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
            Text(
                text = "Despesas",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = corTexto
            )
            TextButton(
                onClick = {
                    adicionarDespesa(
                        Despesa(
                            name = "Nova Despesa",
                            value = "R$ 100,00",
                            date = "01/10/2024",
                            category = "Categoria C"
                        )
                    )
                }
            ) {
                Text(text = "Adicionar Despesa", color = corTexto)
            }
        }

        LazyColumn {
            items(despesas) { despesa ->
                ItemDespesa(despesa = despesa, corTexto = corTexto)
            }
        }
    }
}

@Composable
fun ItemDespesa(despesa: Despesa, corTexto: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .border(1.dp, PretoOpacidade15, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Branco
        )

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(painter = painterResource(id = R.mipmap.icone_dinheiro_despesa),
                    contentDescription = "Imagem Despesa",
                    modifier = Modifier.size(75.dp)
                    .padding(10.dp)
                    )
                Column {
                    Text(
                        text = despesa.category,
                        fontSize = 12.sp,
                        color = Preto,
                    )
                    Text(
                        text = despesa.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = corTexto,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        text = despesa.value,
                        fontSize = 12.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Text(
                text = despesa.date,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .border(1.dp, Verde, RoundedCornerShape(15.dp))
                    .background(VerdeOpacidade15, shape = RoundedCornerShape(10.dp))
                    .padding(4.dp),
                color = Verde
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviaTelaFinancas() {
    TelaFinancas()
}
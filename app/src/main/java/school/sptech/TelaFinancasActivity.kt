package school.sptech

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
                Despesa("Despesa 1", "R$ 150,00", "01/09/2024", "Categoria A"),
                Despesa("Despesa 2", "R$ 300,00", "15/09/2024", "Categoria B")
            )
        )
    }

    if (mostrarSeletorData) {
        SeletorDataDialog(
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
        corValor = Verde,
        adicionarDespesa = { despesa -> despesas.add(despesa) }
    )
}

data class Despesa(val name: String, val value: String, val date: String, val category: String)

@Composable
fun SeletorDataDialog(
    mesSelecionado: String,
    anoSelecionado: Int,
    onDismissRequest: () -> Unit,
    onConfirm: (String, Int) -> Unit
) {
    var mes by remember { mutableStateOf(mesSelecionado) }
    var ano by remember { mutableStateOf(anoSelecionado) }
    var expandedMes by remember { mutableStateOf(false) }
    var expandedAno by remember { mutableStateOf(false) }

    val meses = listOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")
    val anos = (2020..2100).toList()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Selecione o Mês e Ano") },
        text = {
            Column {
                DropdownBox(mes, expandedMes, { expandedMes = true }, { mes = it; expandedMes = false }, meses)
                Spacer(modifier = Modifier.height(16.dp))
                DropdownBox(ano.toString(), expandedAno, { expandedAno = true }, { ano = it.toInt(); expandedAno = false }, anos.map { it.toString() })
            }
        },
        confirmButton = { TextButton(onClick = { onConfirm(mes, ano) }) { Text("Confirmar") } },
        dismissButton = { TextButton(onClick = onDismissRequest) { Text("Cancelar") } }
    )
}

@Composable
fun DropdownBox(
    selectedText: String,
    expanded: Boolean,
    onClick: () -> Unit,
    onItemClick: (String) -> Unit,
    items: List<String>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = selectedText)
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onClick() },
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = { Text(item) },
                onClick = { onItemClick(item) }
            )
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
    corValor: Color,
    adicionarDespesa: (Despesa) -> Unit
) {
    Background()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .padding(top = 16.dp)
    ) {
        Cabecalho(mesSelecionado, anoSelecionado, aoClicarSeletorData, corTitulo)
        ResumoFinanceiro(receitas, despesasTotais, faturamento, comissoes, corValor)
        Spacer(modifier = Modifier.height(16.dp))
        ListaDespesas(despesas, corTitulo, adicionarDespesa)
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
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Finanças",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Preto
        )
        TextButton(onClick = aoClicarSeletorData) {
            Text(
                text = mesSelecionado,
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
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CartaoResumo("Total em Receitas", "R$ +$receitas", VerdeOpacidade15, Verde)
            CartaoResumo("Total em Despesas", "R$ -$despesas", VermelhoOpacidade15, Vermelho)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CartaoResumo("Total em Faturamento", "R$ +$faturamento", AzulOpacidade15, Azul)
            CartaoResumo("Total em Comissões", "R$ -$comissoes", AmareloOpacidade10, Amarelo)
        }
    }
}

@Composable
fun CartaoResumo(titulo: String, valor: String, corFundo: Color, corValor: Color) {
    Box(
        modifier = Modifier
            .width(175.dp)
            .height(100.dp)
            .background(corFundo, shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = titulo,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Preto,
            )
            Text(
                text = valor,
                fontSize = 20.sp,
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
            TextButton(onClick = {
                adicionarDespesa(Despesa("Nova Despesa", "R$ 100,00", "01/10/2024", "Categoria C"))
            }) {
                Text(text = "Adicionar Despesa", color = corTexto)
            }
        }

        LazyColumn {
            items(despesas) { despesa ->
                ItemDespesa(despesa, corTexto)
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
        colors = CardDefaults.cardColors(containerColor = Branco)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.icone_dinheiro_despesa),
                    contentDescription = "Imagem Despesa",
                    modifier = Modifier
                        .size(75.dp)
                        .padding(8.dp)
                )
                Column {
                    Text(
                        text = despesa.category,
                        fontSize = 13.sp,
                        color = Preto,
                    )
                    Text(
                        text = despesa.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = corTexto,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Text(
                        text = despesa.value,
                        fontSize = 18.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp),
                        fontWeight = FontWeight.Bold
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
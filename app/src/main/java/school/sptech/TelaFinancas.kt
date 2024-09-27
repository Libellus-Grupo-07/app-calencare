import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.R
import school.sptech.ui.theme.* // Importe suas cores aqui

@Composable
fun TelaFinancas() {
    val despesas = remember { mutableStateListOf<Despesa>() }

    // Valores simulados que viriam do backend
    val receitas = remember { mutableStateOf(3669.91) }
    val despesasTotais = remember { mutableStateOf(11080.00) }
    val faturamento = remember { mutableStateOf(14749.91) }
    val comissoes = remember { mutableStateOf(8000.00) }

    var selectedMonth by remember { mutableStateOf("Setembro") }
    var selectedYear by remember { mutableStateOf(2024) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Adiciona duas despesas diretamente
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

    FinancialScreenContent(
        selectedMonth = selectedMonth,
        selectedYear = selectedYear,
        showDatePicker = showDatePicker,
        onClickDatePicker = { showDatePicker = true },
        onDismissRequest = { showDatePicker = false },
        onDateSelected = { month, year ->
            selectedMonth = month
            selectedYear = year
            showDatePicker = false
        },
        receitas = receitas.value,
        despesasTotais = despesasTotais.value,
        faturamento = faturamento.value,
        comissoes = comissoes.value,
        despesas = despesas,
        textColor = Preto
    )
}

data class Despesa(
    val name: String,
    val value: String,
    val date: String,
    val category: String
)

@Composable
fun FinancialScreenContent(
    selectedMonth: String,
    selectedYear: Int,
    showDatePicker: Boolean,
    onClickDatePicker: () -> Unit,
    onDismissRequest: () -> Unit,
    onDateSelected: (String, Int) -> Unit,
    receitas: Double,
    despesasTotais: Double,
    faturamento: Double,
    comissoes: Double,
    despesas: List<Despesa>,
    textColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BrancoFundo)
            .padding(16.dp)
    ) {
        Header(
            selectedMonth = selectedMonth,
            selectedYear = selectedYear,
            onClickDatePicker = onClickDatePicker,
            textColor = textColor
        )

        FinancialSummary(receitas, despesasTotais, faturamento, comissoes, textColor)

        Spacer(modifier = Modifier.height(16.dp))
        ExpenseList(despesas = despesas, textColor = textColor)
    }
}

@Composable
fun Header(
    selectedMonth: String,
    selectedYear: Int,
    onClickDatePicker: () -> Unit,
    textColor: Color
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
            onClick = onClickDatePicker
        ) {
            Text(
                text = "$selectedMonth $selectedYear",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Preto
            )
        }
    }
}

@Composable
fun FinancialSummary(
    receitas: Double,
    despesas: Double,
    faturamento: Double,
    comissoes: Double,
    textColor: Color
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryCard(
                title = "Total em Receitas",
                value = "R$ +${receitas}",
                backgroundColor = VerdeOpacidade15,
                textColor = textColor
            )
            SummaryCard(
                title = "Total em Despesas",
                value = "R$ -${despesas}",
                backgroundColor = VermelhoOpacidade15,
                textColor = textColor
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryCard(
                title = "Total em Faturamento",
                value = "R$ +${faturamento}",
                backgroundColor = AzulOpacidade15,
                textColor = textColor
            )
            SummaryCard(
                title = "Total em Comissões",
                value = "R$ -${comissoes}",
                backgroundColor = AmareloOpacidade10,
                textColor = textColor
            )
        }
    }
}

@Composable
fun SummaryCard(title: String, value: String, backgroundColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .width(160.dp)  // Tamanho fixo para a largura
            .height(100.dp) // Tamanho fixo para a altura
            .background(backgroundColor, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Preto
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
                color = textColor
            )
        }
    }
}

@Composable
fun ExpenseList(despesas: List<Despesa>, textColor: Color) {
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
                color = textColor
            )
            TextButton(
                onClick = {
                    // Ação ao clicar em "Adicionar Despesa"
                }
            ) {
                Text(
                    text = "Adicionar Despesa",
                    color = textColor
                )
            }
        }

        despesas.forEach { despesa ->
            ExpenseItem(despesa = despesa, textColor = textColor)
        }
    }
}

@Composable
fun ExpenseItem(despesa: Despesa, textColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.icone_dinheiro_despesa),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 16.dp)
                )
                Column {
                    Text(
                        text = despesa.category,
                        fontSize = 12.sp,
                        color = Preto
                    )
                    Text(
                        text = despesa.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = textColor
                    )
                    Text(
                        text = despesa.date,
                        fontSize = 12.sp,
                        color = Color.Green
                    )
                }
            }
            Text(
                text = despesa.value,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterEnd),
                color = textColor
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewTelaFinancas() {
    TelaFinancas()
}

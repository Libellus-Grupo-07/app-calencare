package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.R

class TelaAdicionarDespesa : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                TelaAddDespesa()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAddDespesa(navController: NavHostController = rememberNavController()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.adicionar_despesa),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Ação do voltar */ }) {
                        Image(
                            painter = painterResource(id = R.mipmap.voltar),
                            contentDescription = "voltar",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            DespesaForm()
            ButtonRow()
        }
    }
}

@Composable
fun DespesaForm() {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var formaPagamento by remember { mutableStateOf("") }
    var dataPagamento by remember { mutableStateOf("") }

    val categories = listOf("Alimentação", "Transporte", "Lazer") // Categories list
    val formasPagamento = listOf("Dinheiro", "Cartão", "Pix") // Payment methods list

    Column(modifier = Modifier.fillMaxWidth()) {

        // Name Field
        FormFieldWithLabel(
            value = name,
            onValueChange = { name = it },
            label = stringResource(R.string.nome),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category Dropdown
        DropdownFieldWithLabel(
            value = category,
            onValueChange = { category = it },
            label = stringResource(R.string.categoria),
            options = categories
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Value Field
        FormFieldWithLabel(
            value = valor,
            onValueChange = { valor = it },
            label = stringResource(R.string.valor),
        )


        Spacer(modifier = Modifier.height(16.dp))

        // Payment Method Dropdown
        DropdownFieldWithLabel(
            value = formaPagamento,
            onValueChange = { formaPagamento = it },
            label = stringResource(R.string.forma_pagamento),
            options = formasPagamento
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormFieldWithLabel(
            value = dataPagamento,
            onValueChange = { dataPagamento = it },
            label = stringResource(R.string.data_pagamento),
        )

    }
}

@Composable
fun DropdownFieldWithLabel(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            readOnly = true, // O campo não permite edição direta
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                }
            }
        )

//@Composable
//fun ButtonRow() {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Button(
//            onClick = { /* Cancel Action */ },
//            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
//        ) {
//            Text(stringResource(R.string.cancelar))
//        }
//
//        Button(
//            onClick = { /* Add Expense Action */ },
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B1FA2))
//        ) {
//            Text(stringResource(R.string.adicionar))
//        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaAdicionarDespesaPreview() {
    CalencareAppTheme {
        TelaAddDespesa()
    }
}

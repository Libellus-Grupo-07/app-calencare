package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import formatarDecimal
import formatarValorMonetario
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.R
import school.sptech.ui.components.Background
import school.sptech.ui.components.DropdownFieldWithLabel
import school.sptech.ui.components.FormButtons
import school.sptech.ui.components.FormFieldWithLabel
import school.sptech.ui.components.TopBarVoltar

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

@Composable
fun TelaAddDespesa(navController: NavHostController = rememberNavController()) {
    Scaffold(
        topBar = {
            TopBarVoltar(
                navController = navController,
                titulo = stringResource(R.string.adicionar_despesa)
            )
        }
    ) { paddingValues ->
        Background()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            DespesaForm()
            FormButtons()
        }
    }
}

@Composable
fun DespesaForm() {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf(0.0) }
    var formaPagamento by remember { mutableStateOf("") }
    var dataPagamento by remember { mutableStateOf("") }

    val categories = listOf("Alimentação", "Transporte", "Lazer") // Categories list
    val formasPagamento = listOf("Dinheiro", "Cartão", "Pix") // Payment methods list

    Column(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(16.dp)) {

        // Name Field
        FormFieldWithLabel(
            value = name,
            onValueChange = { name = it },
            label = stringResource(R.string.nome),
        )

        // Category Dropdown
        DropdownFieldWithLabel(
            value = category,
            onValueChange = { category = it },
            label = stringResource(R.string.categoria),
            options = categories
        )

        // Value Field
        FormFieldWithLabel(
            value = if(valor == 0.0) "" else formatarDecimal(valor.toFloat()),
            isNumericInput = true,
            onValueChange = { valor = it.toDoubleOrNull() ?: 0.0 },
            label = stringResource(R.string.valor),
        )

        // Payment Method Dropdown
        DropdownFieldWithLabel(
            value = formaPagamento,
            onValueChange = { formaPagamento = it },
            label = stringResource(R.string.forma_pagamento),
            options = formasPagamento
        )

        FormFieldWithLabel(
            value = dataPagamento,
            onValueChange = { dataPagamento = it },
            isDateInput = true,
            label = stringResource(R.string.data_pagamento),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaAdicionarDespesaPreview() {
    CalencareAppTheme {
        TelaAddDespesa()
    }
}

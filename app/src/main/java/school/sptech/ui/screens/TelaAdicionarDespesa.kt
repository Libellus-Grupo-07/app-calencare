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
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.R
import school.sptech.ui.components.Background
import school.sptech.ui.components.DropdownFieldWithLabel
import school.sptech.ui.components.FormButtons
import school.sptech.ui.components.FormFieldWithLabel
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.viewModel.DespesaViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import formatarData
import school.sptech.ui.components.AlertError
import java.time.LocalDate

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
fun TelaAddDespesa(
    viewModel: DespesaViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    viewModel.getCategoriasDespesa()
    var deuRuim by remember { mutableStateOf(viewModel.deuErro) }
    var msg by remember { mutableStateOf(viewModel.erro) }

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
            DespesaForm(viewModel)
            FormButtons(
                onCancelClick = { navController.popBackStack() },
                onAddClick = {
                    viewModel.adicionarDespesa()
                    deuRuim = viewModel.deuErro
                    msg = viewModel.erro

                    if (!deuRuim) {
                        navController.popBackStack()
                    }
                }
            )
        }
    }

    if (deuRuim) {
        AlertError(msg = msg)
    }
}

@Composable
fun DespesaForm(viewModel: DespesaViewModel) {
    val formasPagamento = listOf("Dinheiro", "Cartão", "Pix") // Payment methods list

    Column(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(16.dp)) {

        // Name Field
        FormFieldWithLabel(
            value = viewModel.despesa.nome ?: "",
            onValueChange = {
                viewModel.despesa = viewModel.despesa.copy(nome = it)
            },
            label = stringResource(R.string.nome),
        )
        
     // Name Field
        FormFieldWithLabel(
            value = viewModel.despesa.observacao ?: "",
            onValueChange = { viewModel.despesa = viewModel.despesa.copy(observacao = it) },
            label = stringResource(R.string.observacao),
        )

        // Category Dropdown
        DropdownFieldWithLabel(
            value = viewModel.categoriaDespesa.nome ?: "",
            onValueChange = {
                viewModel.categoriaDespesa = viewModel.categoriaDespesa.copy(nome = it)
            },
            label = stringResource(R.string.categoria),
            options = viewModel.listaCategoriasDespesa.map { it.nome ?: "" }
        )

        // Value Field
        FormFieldWithLabel(
            value = viewModel.despesa.valor ?: "0,00",
            isNumericInput = true,
            onValueChange = {
                val valor = it.toString().replace(",", "").replace(".", "")
//                val valorFormatado = valor.toString()
                val valorFormatado = formatarDecimal(valor.toDouble(), isValueInput = true)
                viewModel.despesa = viewModel.despesa.copy(valor = valorFormatado)
            },
            label = stringResource(R.string.valor),
        )

        // Payment Method Dropdown
        DropdownFieldWithLabel(
            value = viewModel.despesa.formaPagamento ?: "",
            onValueChange = { viewModel.despesa = viewModel.despesa.copy(formaPagamento = it) },
            label = stringResource(R.string.forma_pagamento),
            options = formasPagamento
        )

        FormFieldWithLabel(
            value = viewModel.despesa.dtPagamento ?: "",
            onValueChange = {
                viewModel.despesa = viewModel.despesa.copy(dtPagamento = it)
            },
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

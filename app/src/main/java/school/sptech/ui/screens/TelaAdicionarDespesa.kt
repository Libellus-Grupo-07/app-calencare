package school.sptech.ui.screens

import android.annotation.SuppressLint
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
import formatarDataDatePicker
import isDataValida
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import school.sptech.Routes
import school.sptech.navigation.NavBar
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.AlertSuccess
import school.sptech.ui.components.LabelInput
import school.sptech.ui.components.TituloMedium
import school.sptech.ui.theme.Preto
import kotlin.coroutines.cancellation.CancellationException

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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TelaAddDespesa(
    viewModel: DespesaViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    var nomeVazio by remember { mutableStateOf(false) }
    var observacaoVazia by remember { mutableStateOf(false) }
    var categoriaVazia by remember { mutableStateOf(false) }
    var valorVazio by remember { mutableStateOf(false) }
    var formaPagamentoVazio by remember { mutableStateOf(false) }
    var dtPagamentoVazio by remember { mutableStateOf(false) }
    var dtPagamentoFutura by remember { mutableStateOf(false) }

    LaunchedEffect("categorias") {
        viewModel.getCategoriasDespesa()
    }

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
            Spacer(modifier = Modifier.height(0.dp))

            DespesaForm(
                viewModel = viewModel,
                nomePreenchido = nomeVazio,
                observacaoPreenchida = observacaoVazia,
                categoriaPreenchida = categoriaVazia,
                valorPreenchido = valorVazio,
                formaPagamentoPreenchido = formaPagamentoVazio,
                dtPagamentoPreenchido = dtPagamentoVazio,
                onChangeDtPagamento = {
                    viewModel.despesa = viewModel.despesa.copy(dtPagamento = it)

                    val dtPagamentoString = formatarDataDatePicker(
                        data = it.toLongOrNull() ?: 0L,
                        inputFormat = true
                    )
                    dtPagamentoFutura = !isDataValida(dtPagamentoString)
                    dtPagamentoVazio = it.isEmpty()
                },
                dtPagamentoFutura = dtPagamentoFutura
            )

            FormButtons(
                onCancelClick = { navController.popBackStack() },
                onAddClick = {
                    nomeVazio = viewModel.despesa.nome?.isEmpty() ?: true
                    observacaoVazia = viewModel.despesa.observacao?.isEmpty() ?: true
                    categoriaVazia = viewModel.despesa.categoriaDespesaNome?.isEmpty() ?: true
                    valorVazio =
                        viewModel.despesa.valor!!.isEmpty() || viewModel.despesa.valor!!.equals("0,00")
                    formaPagamentoVazio = viewModel.despesa.formaPagamento?.isEmpty() ?: true
                    val dtPagamentoString = formatarDataDatePicker(
                        data = viewModel.despesa.dtPagamento?.toLongOrNull() ?: 0L,
                        inputFormat = true
                    )
                    dtPagamentoFutura = !isDataValida(dtPagamentoString)
                    dtPagamentoVazio =
                        viewModel.despesa.dtPagamento?.isEmpty() ?: true

                    if (
                        !nomeVazio &&
                        !observacaoVazia &&
                        !categoriaVazia &&
                        !valorVazio &&
                        !formaPagamentoVazio &&
                        !dtPagamentoVazio && !dtPagamentoFutura
                    ) {
                        viewModel.adicionarDespesa()
                    }
                }
            )
        }

        if (viewModel.deuErro) {
            AlertError(msg = viewModel.erro)

            LaunchedEffect(key1 = "success") {
                delay(5000)
                viewModel.deuErro = false
            }
        }

        if (!viewModel.deuErro && viewModel.erro.isNotEmpty()) {
            AlertSuccess(msg = "Despesa adicionada com sucesso!")

            DisposableEffect(key1 = "Sucess") {
                val job = CoroutineScope(Dispatchers.Main).launch {
                    try {
                        delay(3000)
                        viewModel.erro = ""
                        navController.navigate(NavBar.Despesas.route) {
                            popUpTo(Routes.AdicionarDespesa.route) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }

                    } catch (e: CancellationException) {
                        throw e
                    }
                }
                onDispose {
                    job.cancel()
                }
            }
        }
    }

}

@Composable
fun DespesaForm(
    viewModel: DespesaViewModel,
    nomePreenchido: Boolean = false,
    observacaoPreenchida: Boolean = false,
    categoriaPreenchida: Boolean = false,
    valorPreenchido: Boolean = false,
    formaPagamentoPreenchido: Boolean = false,
    dtPagamentoPreenchido: Boolean = false,
    dtPagamentoFutura: Boolean = false,
    onChangeDtPagamento: (String) -> Unit
) {
    val formasPagamento = listOf("Dinheiro", "Cartão", "Pix") // Payment methods list

    Column(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(4.dp)) {

        // Name Field
        FormFieldWithLabel(
            value = viewModel.despesa.nome ?: "",
            onValueChange = {
                viewModel.despesa = viewModel.despesa.copy(nome = it)
            },
            label = stringResource(R.string.nome),
            error = nomePreenchido
        )

        // Name Field
        FormFieldWithLabel(
            value = viewModel.despesa.observacao ?: "",
            onValueChange = { viewModel.despesa = viewModel.despesa.copy(observacao = it) },
            label = stringResource(R.string.observacao),
            error = observacaoPreenchida,
            minSize = 5
        )

        // Category Dropdown
        DropdownFieldWithLabel(
            value = viewModel.despesa.categoriaDespesaNome ?: "",
            onValueChange = {
                viewModel.despesa = viewModel.despesa.copy(categoriaDespesaNome = it)
            },
            label = stringResource(R.string.categoria),
            options = viewModel.listaCategoriasDespesa.map { it.nome ?: "" },
            error = categoriaPreenchida,
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
            error = valorPreenchido
        )

        // Payment Method Dropdown
        DropdownFieldWithLabel(
            value = viewModel.despesa.formaPagamento ?: "",
            onValueChange = {
                viewModel.despesa = viewModel.despesa.copy(formaPagamento = it)
            },
            label = stringResource(R.string.forma_pagamento),
            options = formasPagamento,
            error = formaPagamentoPreenchido
        )

        FormFieldWithLabel(
            value = viewModel.despesa.dtPagamento ?: "",
            onValueChange = {
                onChangeDtPagamento(it)
            },
            isDateInput = true,
            label = stringResource(R.string.data_pagamento),
            error = dtPagamentoPreenchido || dtPagamentoFutura,
            isDatePastOrPresent = dtPagamentoFutura
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
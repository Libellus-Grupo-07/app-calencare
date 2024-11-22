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
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.R
import school.sptech.ui.components.Background
import school.sptech.ui.components.FormButtons
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.viewModel.DespesaViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import formatarDataDatePicker
import getLongDate
import isDataValida
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import school.sptech.Routes
import school.sptech.navigation.NavBar
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.AlertSuccess
import school.sptech.ui.components.ButtonIconExcluir
import school.sptech.ui.components.ModalConfirmarExclusao
import kotlin.coroutines.cancellation.CancellationException

class TelaInformacoesDespesa : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                TelaInformacoesDespesaScreen()
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TelaInformacoesDespesaScreen(
    viewModel: DespesaViewModel = viewModel(),
    despesaId: Int = 0,
    navController: NavHostController = rememberNavController()
) {
    var nomePreenchido by remember { mutableStateOf(true) }
    var observacaoPreenchida by remember { mutableStateOf(true) }
    var categoriaPreenchida by remember { mutableStateOf(true) }
    var valorPreenchido by remember { mutableStateOf(true) }
    var formaPagamentoPreenchida by remember { mutableStateOf(true) }
    var dtPagamentoPreenchida by remember { mutableStateOf(true) }

    var dtPagamentoFutura by remember { mutableStateOf(false) }
    var exibirModalExcluir by remember { mutableStateOf(false) }

    LaunchedEffect("categorias") {
        viewModel.getCategoriasDespesa()
        viewModel.getDespesaById(despesaId = despesaId)
        viewModel.despesa.categoriaDespesaNome
    }

    Scaffold(
        topBar = {
            TopBarVoltar(
                navController = navController,
                titulo = stringResource(R.string.informacoes_despesa),
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
            DespesaForm(
                viewModel = viewModel,
                nomePreenchido = !nomePreenchido,
                observacaoPreenchida = !observacaoPreenchida,
                categoriaPreenchida = !categoriaPreenchida,
                valorPreenchido = !valorPreenchido,
                formaPagamentoPreenchido = !formaPagamentoPreenchida,
                dtPagamentoPreenchido = !dtPagamentoPreenchida,
                dtPagamentoFutura = dtPagamentoFutura,
                onChangeDtPagamento = {
                    viewModel.despesa = viewModel.despesa.copy(dtPagamento = it)

                    val dtPagamentoString = formatarDataDatePicker(
                        data = it.toLongOrNull() ?: 0L,
                        inputFormat = true
                    )
                    dtPagamentoFutura = !isDataValida(dtPagamentoString)
                    dtPagamentoPreenchida = it.isNotEmpty()
                }
            )

            FormButtons(
                onCancelClick = { navController.popBackStack() },
                onAddClick = {
                    nomePreenchido = viewModel.despesa.nome?.isNotEmpty() ?: false
                    observacaoPreenchida = viewModel.despesa.observacao?.isNotEmpty() ?: false
                    categoriaPreenchida = viewModel.despesa.categoriaDespesaNome?.isNotEmpty() ?: false
                    valorPreenchido =
                        viewModel.despesa.valor!!.isNotEmpty() && viewModel.despesa.valor != "0,00"
                    formaPagamentoPreenchida = viewModel.despesa.formaPagamento?.isNotEmpty() ?: false
                    val dtPagamento = viewModel.despesa.dtPagamento
                    val longDate = dtPagamento?.toLongOrNull() ?: getLongDate(dtPagamento!!)
                    val dtPagamentoString = formatarDataDatePicker(
                        data = longDate,
                        inputFormat = true
                    )
                    dtPagamentoFutura = !isDataValida(dtPagamentoString)
                    dtPagamentoPreenchida =
                        viewModel.despesa.dtPagamento?.isNotEmpty() ?: false

                    if(
                        nomePreenchido &&
                        observacaoPreenchida &&
                        categoriaPreenchida &&
                        valorPreenchido &&
                        formaPagamentoPreenchida &&
                        dtPagamentoPreenchida &&
                        !dtPagamentoFutura
                    ) {
                        viewModel.salvarDespesa()
                    }

                },
                addButtonText = stringResource(R.string.salvar)
            )

            ButtonIconExcluir(onClick = {
                exibirModalExcluir = true
            })
        }

        if (exibirModalExcluir) {
            ModalConfirmarExclusao(
                onDismiss = { exibirModalExcluir = false },
                onConfirm = {
                    viewModel.deletarDespesa()
                    exibirModalExcluir = false
                },
                titulo = "Excluir despesa",
                texto = "Você tem certeza que deseja excluir a despesa ",
                nomeItem = viewModel.despesa.nome ?: ""
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
            AlertSuccess(msg = viewModel.erro)

            DisposableEffect(key1 = "Sucess") {
                val job = CoroutineScope(Dispatchers.Main).launch {
                    try {
                        delay(3000)
                        viewModel.erro = ""
                        navController.navigate(NavBar.Despesas.route) {
                            popUpTo(Routes.InformacoesDespesa.route) {
                                saveState = true
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaEditarDespesaPreview() {
    CalencareAppTheme {
        TelaInformacoesDespesaScreen()
    }
}
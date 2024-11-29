package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import formatarDataDatePicker
import getColorTextEstoque
import getEnabledButtonRetirarEstoque
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import school.sptech.R
import school.sptech.Routes
import school.sptech.data.model.Produto
import school.sptech.dataStoreRepository
import school.sptech.navigation.NavBar
import school.sptech.ui.components.AlertError
import school.sptech.ui.components.AlertSuccess
import school.sptech.ui.components.Background
import school.sptech.ui.components.ButtonBackground
import school.sptech.ui.components.ButtonIconExcluir
import school.sptech.ui.components.ButtonOutline
import school.sptech.ui.components.DatePickerModal
import school.sptech.ui.components.DropdownFieldWithLabel
import school.sptech.ui.components.FormFieldWithLabel
import school.sptech.ui.components.LabelInput
import school.sptech.ui.components.ModalConfirmarExclusao
import school.sptech.ui.components.ReporProductModal
import school.sptech.ui.components.RetirarProductModal
import school.sptech.ui.components.SelectableDatesRow
import school.sptech.ui.components.TopBarInformacoesProduto
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.viewModel.ProdutoViewModel
import school.sptech.ui.viewModel.ReporProdutoViewModel
import school.sptech.ui.viewModel.ValidadeViewModel
import transformarEmLocalDateTime
import kotlin.coroutines.cancellation.CancellationException

class TelaInformacoesProduto : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalencareAppTheme {
                TelaInformacoesProdutoScreen(navController = rememberNavController())
            }
        }
    }
}

@Composable
fun TelaInformacoesProdutoScreen(
    produtoViewModel: ProdutoViewModel = viewModel(),
    validadeViewModel: ValidadeViewModel = viewModel(),
    reporProdutoViewModel: ReporProdutoViewModel = viewModel(),
    navController: NavHostController,
    idProduto: Int = 0,
) {

    LaunchedEffect(Unit) {
        val idEmpresa = dataStoreRepository.getEmpresaId()
        produtoViewModel.getCategoriasProduto()
        produtoViewModel.getProdutoById(empresaId = idEmpresa, produtoId = idProduto)
        validadeViewModel.getValidades(idProduto)
    }

    val produto = produtoViewModel.getProdutoAtual()
    // produto.qntdTotalEstoque = validadeViewModel.getTotalEstoqueProduto(idProduto)

    val validades = validadeViewModel.getValidades(idProduto)
    produto.validades = validades

    val listaValidades = validades.map { it.dtValidade ?: "" }

    var exibirModalRetirar by remember {
        mutableStateOf(false)
    }
    var exibirModalRepor by remember {
        mutableStateOf(false)
    }

    var exibirAdicionarData by remember {
        mutableStateOf(false)
    }

    var exibirModalExcluir by remember {
        mutableStateOf(false)
    }

    var dateValue by remember { mutableStateOf(0L) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopBarInformacoesProduto(
                    onClickSalvar = {
                        produtoViewModel.atualizarProduto()
                    },
                    onClickVoltar = { navController.popBackStack() }
                )
            }
        ) { paddingValues ->
            Background()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 0.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Form(
                    produto = produto,
                    viewModel = produtoViewModel,
                    validadeViewModel = validadeViewModel
                )

                StockQuantity(produto?.qntdTotalEstoque ?: 0)

                BoxButtons(
                    enabledButtonRetirar = getEnabledButtonRetirarEstoque(
                        produto?.qntdTotalEstoque ?: 0
                    ),
                    onRetirarClick = {
                        validadeViewModel.deuErro = false
                        exibirModalRetirar = true
                    },
                    onReporClick = {
                        validadeViewModel.deuErro = false
                        exibirModalRepor = true
                    }
                )

                Spacer(modifier = Modifier.height(2.dp))

                ButtonIconExcluir(
                    onClick = {
                        exibirModalExcluir = true
                    }
                )

            }
        }

        if (exibirModalExcluir) {
            ModalConfirmarExclusao(
                onDismiss = {
                    exibirModalExcluir = false
                },
                onConfirm = {
                    produtoViewModel.excluirProduto()
                    exibirModalExcluir = false
                },
                titulo = "Excluir Produto",
                texto = "Você tem certeza que deseja excluir o produto ",
                nomeItem = produto.nome ?: ""
            )
        }

        // Exibe o modal de reposição de produto
        if (exibirModalRepor) {
            ReporProductModal(
                onDismiss = {
                    exibirModalRepor = false
                    reporProdutoViewModel.setQuantidadeInicial(0)
                    reporProdutoViewModel.setQuantidadeMaxima(0)
                    reporProdutoViewModel.quantidadeEstoqueData.value = 0
                },
                produto = produto.nome ?: "",
                quantidadeEstoque = produto.qntdTotalEstoque ?: 0,
                viewModel = reporProdutoViewModel,
                onDateSelected = {
                    reporProdutoViewModel.quantidadeEstoqueData.value = 0

                    val validade = validades.find { validadeAtual ->
                        it!!.equals(validadeAtual.dtValidade ?: "")
                    }

                    validadeViewModel.validade = validade!!
                    reporProdutoViewModel.quantidadeEstoqueData.value =
                        validadeViewModel.getQuantidadeEstoqueDaValidade()

                    reporProdutoViewModel.setQuantidadeMaxima(null)
                    reporProdutoViewModel.setQuantidadeInicial(0)

                },
                onQuantidadeChanged = {
                    validadeViewModel.movimentacaoValidade =
                        validadeViewModel.movimentacaoValidade.copy(quantidade = it)
                },
                onClickAdicionarData = {
                    exibirAdicionarData = true
                },
                onConfirm = {
                    validadeViewModel.reporEstoque()
                },
                datesFromBackend = listaValidades,
            )

        }


        if (exibirAdicionarData) {
            DatePickerModal(
                dateSelected = dateValue,
                onDismiss = {
                    exibirAdicionarData = false
                },
                onDateSelected = { date ->
                    validadeViewModel.validade =
                        validadeViewModel.validade.copy(idProduto = produto.id ?: 0)
                    validadeViewModel.validade = validadeViewModel.validade.copy(
                        dtValidade = transformarEmLocalDateTime(
                            formatarDataDatePicker(
                                inputFormat = true,
                                data = date
                            )
                        ).toString()
                    )

                    validadeViewModel.adicionarValidade()
                    exibirAdicionarData = false
                }
            )
        }

        if (produtoViewModel.deuErro) {
            AlertError(
                msg = produtoViewModel.mensagem
            )

            LaunchedEffect("error") {
                delay(6000)
                produtoViewModel.deuErro = false
            }
        }

        if (!produtoViewModel.deuErro && produtoViewModel.mensagem.isNotEmpty()) {
            AlertSuccess(msg = produtoViewModel.mensagem)

            DisposableEffect(key1 = "Sucess") {
                val job = CoroutineScope(Dispatchers.Main).launch {
                    try {
                        delay(3000)
                        produtoViewModel.mensagem = ""
                        val ultimaRota =
                            navController.previousBackStackEntry?.destination?.route
                        navController.navigate(ultimaRota ?: NavBar.Inicio.route) {
                            popUpTo(Routes.InformacoesProduto.route) {
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


        // Exibe o modal de retirada de produto
        if (exibirModalRetirar) {
            RetirarProductModal(
                produto = produto.nome ?: "",
                viewModel = reporProdutoViewModel,
                onDateSelected = {
                    reporProdutoViewModel.quantidadeEstoqueData.value = 0

                    val validade = validades.find { validadeAtual ->
                        it!!.equals(validadeAtual.dtValidade ?: "")
                    }
                    validadeViewModel.validade = validade!!

                    val qtdMaxima = validadeViewModel.getQuantidadeEstoqueDaValidade()

                    reporProdutoViewModel.quantidadeEstoqueData.value = qtdMaxima
                    reporProdutoViewModel.setQuantidadeMaxima(qtdMaxima)
                    reporProdutoViewModel.setQuantidadeInicial(0)
                },
                onQuantidadeChanged = {
                    validadeViewModel.movimentacaoValidade =
                        validadeViewModel.movimentacaoValidade.copy(quantidade = it)
                },
                quantidadeEstoque = produto.qntdTotalEstoque ?: 0,
                onDismiss = {
                    exibirModalRetirar = false
                    reporProdutoViewModel.setQuantidadeInicial(0)
                    reporProdutoViewModel.setQuantidadeMaxima(0)
                    reporProdutoViewModel.quantidadeEstoqueData.value = 0

                },
                onConfirm = {
                    validadeViewModel.retirarEstoque()
                },
                datesFromBackend = listaValidades
            )
        }

        if (validadeViewModel.deuErro) {
            exibirModalRepor = false
            exibirModalRetirar = false
            AlertError(msg = validadeViewModel.erro)
        }

        if (!validadeViewModel.deuErro && validadeViewModel.erro.isNotEmpty()) {
            exibirModalRepor = false
            exibirModalRetirar = false

            reporProdutoViewModel.setQuantidadeInicial(0)
            reporProdutoViewModel.setQuantidadeMaxima(0)
            reporProdutoViewModel.quantidadeEstoqueData.value = 0
            AlertSuccess(msg = "Estoque atualizado com sucesso!")

            LaunchedEffect("success") {
                delay(6000)
                validadeViewModel.erro = ""
            }
        }
    }
}

@Composable
fun BoxButtons(
    enabledButtonRetirar: Boolean = true,
    onRetirarClick: () -> Unit = { /* TODO: Handle cancel */ },
    onReporClick: () -> Unit = { /* TODO: Handle add */ }
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ButtonOutline(
            enabledButton = enabledButtonRetirar,
            titulo = stringResource(id = R.string.retirar),
            iconId = R.mipmap.menos,
            onClick = onRetirarClick
        )

        Spacer(modifier = Modifier.size(4.dp))

        ButtonBackground(
            titulo = stringResource(R.string.repor),
            cor = RoxoNubank,
            iconPainter = Icons.Rounded.Add,
            onClick = onReporClick
        )

    }
}

@Composable
fun Form(
    validadeViewModel: ValidadeViewModel,
    produto: Produto, viewModel: ProdutoViewModel
) {
    var qtdEstoqueData by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
    ) {
        FormFieldWithLabel(
            value = produto.nome ?: "",
            onValueChange = {
                viewModel.produto = viewModel.produto.copy(nome = it)
                produto.nome = it
            },
            label = stringResource(R.string.nome)
        )

        FormFieldWithLabel(
            value = produto.marca ?: "",
            onValueChange = {
                viewModel.produto = viewModel.produto.copy(marca = it)
                produto.marca = it
            },
            label = stringResource(R.string.marca)
        )

        DropdownFieldWithLabel(
            value = produto.categoriaProdutoNome ?: "",
            onValueChange = {
                viewModel.produto = viewModel.produto.copy(categoriaProdutoNome = it)
                produto.categoriaProdutoNome = it
            },
            label = stringResource(R.string.categoria),
            options = viewModel.categoriasProduto.map { it.nome ?: "" }
        )

        FormFieldWithLabel(
            value = produto.descricao ?: "",
            onValueChange = {
                viewModel.produto = viewModel.produto.copy(descricao = it)
                produto.descricao = it
            },
            label = stringResource(R.string.descricao),
            isMultiline = true
        )

        SelectableDatesRow(
            dates = produto.validades.let { it?.map { date -> date.dtValidade ?: ""} } ?: emptyList(),
            qtdEstoqueData = qtdEstoqueData,
            onDateSelected = { date ->
                val validade = produto.validades?.find { validadeAtual ->
                    date.equals(validadeAtual.dtValidade ?: "")
                }

                validadeViewModel.validade = validade!!
                qtdEstoqueData = validadeViewModel.getQuantidadeEstoqueDaValidade()

            }
        )
    }
}

@Composable
fun StockQuantity(qtdEstoque: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        LabelInput(
            label = stringResource(R.string.totalEmEstoque)
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = stringResource(R.string.qtdEmEstoque, qtdEstoque),
            color = getColorTextEstoque(qtdEstoque),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            fontFamily = fontFamilyPoppins
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaInformacoesProdutoPreview() {
    CalencareAppTheme {
        TelaInformacoesProdutoScreen(navController = rememberNavController())
    }
}

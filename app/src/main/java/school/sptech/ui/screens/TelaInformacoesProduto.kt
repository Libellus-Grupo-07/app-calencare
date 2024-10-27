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
import formatarData
import getColorTextEstoque
import getEnabledButtonRetirarEstoque
import school.sptech.R
import school.sptech.data.model.Produto
import school.sptech.preferencesHelper
import school.sptech.ui.components.Background
import school.sptech.ui.components.ButtonBackground
import school.sptech.ui.components.ButtonOutline
import school.sptech.ui.components.DropdownFieldWithLabel
import school.sptech.ui.components.FormFieldWithLabel
import school.sptech.ui.components.LabelInput
import school.sptech.ui.components.ReporProductModal
import school.sptech.ui.components.RetirarProductModal
import school.sptech.ui.components.TopBarInformacoesProduto
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.fontFamilyPoppins
import school.sptech.ui.viewModel.ProdutoViewModel
import school.sptech.ui.viewModel.ReporProdutoViewModel
import school.sptech.ui.viewModel.ValidadeViewModel

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
    idEmpresa: Int = preferencesHelper.getIdEmpresa()
) {
    produtoViewModel.getCategoriasProduto()

    var msg by remember {
        mutableStateOf("")
    }

    var deuRuim by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        produtoViewModel.getProdutoById(empresaId = idEmpresa, produtoId = idProduto)
    }

    val produto = produtoViewModel.getProdutoAtual()
    produto.qtdEstoque = validadeViewModel.getTotalEstoqueProduto(idProduto)

    val validades = validadeViewModel.getValidades(idProduto)
    val listaValidades = validades.toList().map {
        it.dtValidade?.let { data ->
            formatarData(
                data
            )
        } ?: ""
    }

    var exibirModalRetirar by remember {
        mutableStateOf(false)
    }
    var exibirModalRepor by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopBarInformacoesProduto(
                    onClickSalvar = { /*TODO*/ },
                    onClickVoltar = { navController.popBackStack() }
                )
            }
        ) { paddingValues ->
            Background()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Form(produto, produtoViewModel)

                StockQuantity(produto?.qtdEstoque ?: 0)

                BoxButtons(
                    enabledButtonRetirar = getEnabledButtonRetirarEstoque(
                        produto?.qtdEstoque ?: 0
                    ),
                    onRetirarClick = {
                        exibirModalRetirar = true
                    },
                    onReporClick = {
                        exibirModalRepor = true
                    }
                )
            }
        }

        // Exibe o modal de reposição de produto
        if (exibirModalRepor) {
            ReporProductModal(
                onDismiss = {
                    exibirModalRepor = false
                    reporProdutoViewModel.setQuantidadeInicial(0)
                    reporProdutoViewModel.setQuantidadeMaxima(0)
                },
                produto = produto.nome ?: "",
                quantidadeEstoque = produto.qtdEstoque ?: 0,
                viewModel = reporProdutoViewModel,
                onDateSelected = {
                    val validade =
                        validades.find { validade ->
                            formatarData(validade.dtValidade!!).equals(it)
                        }
                    validadeViewModel.validade = validade!!
                    reporProdutoViewModel.quantidadeEstoqueData.value =
                        validadeViewModel.getQuantidadeEstoqueValidade(validade.id!!)

                    reporProdutoViewModel.setQuantidadeMaxima(0)

                },
                onQuantidadeChanged = {
                    validadeViewModel.movimentacaoValidade =
                        validadeViewModel.movimentacaoValidade.copy(quantidade = it)
                },
                onConfirm = {
                    msg = ""
                    validadeViewModel.reporEstoque()
                    msg = validadeViewModel.erro
                    deuRuim = validadeViewModel.deuErro

                    if (!deuRuim && msg.isNotEmpty()) {
                        exibirModalRepor = false
                        reporProdutoViewModel.setQuantidadeInicial(0)
                        reporProdutoViewModel.setQuantidadeMaxima(0)
                    }
                },
                datesFromBackend = listaValidades,
            )

        }

        // Exibe o modal de retirada de produto
        if (exibirModalRetirar) {
            RetirarProductModal(
                produto = produto.nome ?: "",
                viewModel = reporProdutoViewModel,
                onDateSelected = {
                    val validade =
                        validades.find { validade ->
                            formatarData(validade.dtValidade!!).equals(it)
                        }
                    validadeViewModel.validade = validade!!
                    reporProdutoViewModel.quantidadeEstoqueData.value =
                        validadeViewModel.getQuantidadeEstoqueValidade(validade.id!!)

                    reporProdutoViewModel.setQuantidadeMaxima(reporProdutoViewModel.quantidadeEstoqueData.value)
                },
                onQuantidadeChanged = {
                    validadeViewModel.movimentacaoValidade =
                        validadeViewModel.movimentacaoValidade.copy(quantidade = it)
                },
                quantidadeEstoque = produto.qtdEstoque ?: 0,
                onDismiss = {
                    exibirModalRetirar = false
                    reporProdutoViewModel.setQuantidadeInicial(0)
                    reporProdutoViewModel.setQuantidadeMaxima(0)
                },
                onConfirm = {
                    msg = ""
                    validadeViewModel.retirarEstoque()
                    msg = validadeViewModel.erro
                    deuRuim = validadeViewModel.deuErro

                    if (!deuRuim && msg.isNotEmpty()) {
                        exibirModalRetirar = false
                        reporProdutoViewModel.setQuantidadeInicial(0)
                        reporProdutoViewModel.setQuantidadeMaxima(0)
                    }
                },
                datesFromBackend = listaValidades
            )
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
fun Form(produto: Produto, viewModel: ProdutoViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        Arrangement.spacedBy(16.dp)
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

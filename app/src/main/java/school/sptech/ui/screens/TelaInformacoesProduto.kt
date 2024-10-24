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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import getEnabledButtonRetirarEstoque
import school.sptech.R
import school.sptech.ui.components.Background
import school.sptech.ui.components.ButtonBackground
import school.sptech.ui.components.ButtonOutline
import school.sptech.ui.components.DropdownFieldWithLabel
import school.sptech.ui.components.FormFieldWithLabel
import school.sptech.ui.components.LabelInput
import school.sptech.ui.components.TopBarInformacoesProduto
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.fontFamilyPoppins

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInformacoesProdutoScreen(navController: NavHostController) {
    var estoque by remember { mutableStateOf(0) }

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
                Form()

                StockQuantity(estoque)

                BoxButtons(enabledButtonRetirar = getEnabledButtonRetirarEstoque(estoque))
            }
        }
    }
}

@Composable
fun BoxButtons(
    enabledButtonRetirar: Boolean = true,
    onCancelClick: () -> Unit = { /* TODO: Handle cancel */ },
    onAddClick: () -> Unit = { /* TODO: Handle add */ }
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
            onClick = { /*TODO*/ }
        )

        Spacer(modifier = Modifier.size(4.dp))

        ButtonBackground(
            titulo = stringResource(R.string.repor),
            cor = RoxoNubank,
            iconPainter = Icons.Rounded.Add,
            onClick = { /*TODO*/ }
        )

    }
}

@Composable
fun Form() {
    var name by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        Arrangement.spacedBy(16.dp)
    ) {
        FormFieldWithLabel(
            value = name,
            onValueChange = { name = it },
            label = stringResource(R.string.nome)
        )

        FormFieldWithLabel(
            value = brand,
            onValueChange = { brand = it },
            label = stringResource(R.string.marca)
        )

        DropdownFieldWithLabel(
            value = category,
            onValueChange = { category = it },
            label = stringResource(R.string.categoria),
            options = listOf("Unha", "Cabelo", "Maquiagem")
        )

        FormFieldWithLabel(
            value = description,
            onValueChange = { description = it },
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
            text = stringResource(R.string.qtdEmEstoque,qtdEstoque),
            color = Vermelho,
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

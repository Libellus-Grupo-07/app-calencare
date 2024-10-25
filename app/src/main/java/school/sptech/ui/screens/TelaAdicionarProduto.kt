package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import school.sptech.R
import school.sptech.ui.components.Background
import school.sptech.ui.components.DropdownFieldWithLabel
import school.sptech.ui.components.FormButtons
import school.sptech.ui.components.FormFieldWithLabel
import school.sptech.ui.components.LabelInput
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.theme.CalencareAppTheme

class TelaAdicionarProduto : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalencareAppTheme {
                TelaAdicionarProdutoScreen()
            }
        }
    }
}

@Composable
fun TelaAdicionarProdutoScreen(navController: NavHostController = rememberNavController()) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopBarVoltar(
                    navController = navController,
                    titulo = stringResource(id = R.string.adicionarProduto)
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
                ProductForm()
                FormButtons(onCancelClick = { navController.popBackStack() })
            }
        }
    }
}


@Composable
fun ProductForm() {
    var name by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(16.dp)) {
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

        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween){
            LabelInput(label = stringResource(R.string.possui_validade))
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaAdicionarProdutoPreview() {
    CalencareAppTheme {
        TelaAdicionarProdutoScreen()
    }
}

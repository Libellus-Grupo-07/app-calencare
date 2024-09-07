package school.sptech

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TelaAdicionarProdutoScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Adicionar Produto",
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
                            contentDescription = "Voltar",
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
            Spacer(modifier = Modifier.height(32.dp))

            ProductForm()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = { /* TODO: Handle cancel */ },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent, // Fundo transparente
                        contentColor = Color.Gray // Cor do texto do botão
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Cancelar")
                }
                Button(
                    onClick = { /* TODO: Handle add */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9F36F0))
                ) {
                    Text("Adicionar", color = Color.White)
                }
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

    Column(modifier = Modifier.fillMaxWidth()) {
        FormFieldWithLabel(
            value = name,
            onValueChange = { name = it },
            label = "Nome"
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormFieldWithLabel(
            value = brand,
            onValueChange = { brand = it },
            label = "Marca"
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormFieldWithLabel(
            value = category,
            onValueChange = { category = it },
            label = "Categoria"
        )

        Spacer(modifier = Modifier.height(16.dp))

        FormFieldWithLabel(
            value = description,
            onValueChange = { description = it },
            label = "Descrição",
            isMultiline = true
        )
    }
}

@Composable
fun FormFieldWithLabel(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isMultiline: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        if (isMultiline) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(124.dp)
                    .clip(RoundedCornerShape(20.dp)), // Aumenta o arredondamento
                shape = RoundedCornerShape(20.dp) // Aumenta o arredondamento
            )
        } else {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)), // Aumenta o arredondamento
                shape = RoundedCornerShape(20.dp) // Aumenta o arredondamento
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview2() {
    CalencareAppTheme {
        TelaAdicionarProdutoScreen()
    }
}

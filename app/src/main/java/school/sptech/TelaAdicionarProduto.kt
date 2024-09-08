package school.sptech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
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
@Composable
fun TelaAdicionarProdutoScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Bolinha no canto superior direito
        Canvas(modifier = Modifier
            .size(100.dp)
            .align(Alignment.TopEnd)
            .offset(x = 50.dp, y = (-50).dp)) {
            drawCircle(color = Color(0xFF9F36F0), radius = size.minDimension / 2, style = Fill)
        }

        // Bolinha no canto inferior esquerdo
        Canvas(modifier = Modifier
            .size(100.dp)
            .align(Alignment.BottomStart)
            .offset(x = (-50).dp, y = 50.dp)) {
            drawCircle(color = Color(0xFF9F36F0), radius = size.minDimension / 2, style = Fill)
        }

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

                ButtonRow()
            }
        }
    }
}

@Composable
fun ButtonRow(
    onCancelClick: () -> Unit = { /* TODO: Handle cancel */ },
    onAddClick: () -> Unit = { /* TODO: Handle add */ }
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        OutlinedButton(
            onClick = onCancelClick,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Gray
            ),
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.mipmap.cancelar),
                contentDescription = "Cancelar",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cancelar")
        }
        Button(
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9F36F0))
        ) {
            Icon(
                painter = painterResource(id = R.mipmap.adicionar),
                contentDescription = "Adicionar",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp)) 
            Text("Adicionar", color = Color.White)
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
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .let { if (isMultiline) it.height(124.dp) else it }
                .clip(RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            maxLines = if (isMultiline) Int.MAX_VALUE else 1
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaAdicionarProdutoPreview() {
    CalencareAppTheme {
        TelaAdicionarProdutoScreen()
    }
}

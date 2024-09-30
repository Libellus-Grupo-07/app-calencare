package school.sptech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank

class TelaInformacoesProduto : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalencareAppTheme {
                TelaInformacoesProdutoScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInformacoesProdutoScreen() {
    var estoque by remember { mutableStateOf(0) }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Preto,
                    ),
                    title = {
                        Row {
                        Text(
                            text = stringResource(R.string.informacoesProduto),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                        )
                            Spacer(modifier = Modifier.width(30.dp))
                        Image(
                            painter = painterResource(id = R.mipmap.check),
                            contentDescription = "Check",
                            modifier = Modifier.size(30.dp)
                        )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* Ação do voltar */ }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar",
                                tint = Preto
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Background()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                ProductForm()

                StockQuantity(5)

                Button()
            }
        }
    }
}

@Composable
fun Button(
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
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.retirar))
        }
        Button(
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9F36F0))
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.repor), color = Color.White)
        }
    }
}

@Composable
fun Form() {
    var name by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        FieldWithLabel(
            value = name,
            onValueChange = { name = it },
            label = stringResource(R.string.nome)
        )

        Spacer(modifier = Modifier.height(16.dp))

        FieldWithLabel(
            value = brand,
            onValueChange = { brand = it },
            label = stringResource(R.string.marca)
        )

        Spacer(modifier = Modifier.height(16.dp))

        FieldWithLabel(
            value = category,
            onValueChange = { category = it },
            label = stringResource(R.string.categoria)
        )

        Spacer(modifier = Modifier.height(16.dp))

        FieldWithLabel(
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
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = stringResource(R.string.quantidadeEstoque),
            color = Preto,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(
            text = stringResource(R.string.emEstoque,qtdEstoque),
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldWithLabel(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isMultiline: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Cinza,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RoxoNubank,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Preto,
                unfocusedTextColor = Preto,
                focusedLabelColor = Color.Gray
            ),
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
fun TelaInformacoesProdutoPreview() {
    CalencareAppTheme {
        TelaInformacoesProdutoScreen()
    }
}

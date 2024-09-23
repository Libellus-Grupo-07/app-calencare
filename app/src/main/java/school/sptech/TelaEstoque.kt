package school.sptech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.data.model.Produto
import school.sptech.ui.components.BoxProdutos
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.fontFamily
import school.sptech.ui.theme.letterSpacingPrincipal

class TelaEstoque : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar()
                    }
                ) { innerPadding ->
                    TelaEstoque(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TelaEstoque(modifier: Modifier = Modifier) {
    val listaProdutos = remember {
        mutableListOf(
            Produto("Esmalte Azul Metálico Risqué 8ml", "Unha", 0),
            Produto("Shampoo Mais Lisos Wella 350ml", "Cabelo", 1),
            Produto("Condicionador Wella 350ml", "Cabelo", 1),
            Produto("Máscara de Cílios Volume Up Vult 8g", "Maquiagem", 3),
            Produto("Spray Keune Style Dry Texturizer 300ml", "Cabelo", 4),
            Produto("Base Líquida Estée Lauder  SFS-10", "Maquiagem", 6),
        )
    }

    Background()

    Column(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        BoxProdutos(produtos = listaProdutos, titulo = "Estoque")
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 24.dp, top = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = modifier
                .align(Alignment.CenterVertically)
                .fillMaxHeight(0.08f),
            onClick = { /*TODO*/ },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Preto
            )
        ){
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = "Ícone de Voltar",
                modifier = Modifier.size(32.dp),
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.06f),
            value = text,
            onValueChange = {
                text = it
            },
            leadingIcon = {
                Box(modifier = modifier) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.mipmap.pesquisar),
                        contentDescription = "Ícone de Pesquisar",
                        modifier = Modifier.size(18.dp),
                        tint = Preto
                    )
                }
            },
            placeholder = {
                Text(
                    modifier = modifier,
                    text = "Pesquisar",
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily,
                    letterSpacing = letterSpacingPrincipal,
                    color = Preto,
                    lineHeight = 8.sp
                )
            },
            textStyle = TextStyle(
                textDirection = TextDirection.ContentOrLtr,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = fontFamily,
                letterSpacing = letterSpacingPrincipal,
                color = Preto,
            ),
            shape = CircleShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Preto,
                unfocusedBorderColor = Preto,
                focusedBorderColor = RoxoNubank,
            )
        )

        Row(
            modifier = modifier,
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Preto
                )
            ) {
                Icon(
                    Icons.Rounded.Add,
                    "Ícone de Adicionar",
                    modifier = modifier.size(64.dp)

                )
            }

            IconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Preto
                )
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.mipmap.filtro),
                    "Ícone de Filtro",
                    modifier = modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EstoquePreview() {
    CalencareAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar()
            }
        ) { innerPadding ->
            TelaEstoque(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
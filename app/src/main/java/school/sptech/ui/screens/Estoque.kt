package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import school.sptech.R
import school.sptech.data.model.Produto
import school.sptech.ui.components.Background
import school.sptech.ui.components.BoxProdutos
import school.sptech.ui.components.TopBarEstoque
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.fontFamily
import school.sptech.ui.theme.letterSpacingPrincipal

class Estoque : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaEstoque(rememberNavController())
        }
    }
}

@Composable
fun TelaEstoque(navController: NavController){
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

    Column() {
        TopBarEstoque(navController = navController)
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            BoxProdutos(
                produtos = listaProdutos,
                titulo = stringResource(id = R.string.estoque),
                isTelaInicio = false,
            )
        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EstoquePreview() {
    TelaEstoque(rememberNavController())
}
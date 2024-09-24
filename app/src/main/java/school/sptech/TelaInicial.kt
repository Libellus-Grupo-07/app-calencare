package school.sptech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.data.model.Produto
import school.sptech.ui.components.BoxKpisEstoque
import school.sptech.ui.components.BoxProdutos
import school.sptech.ui.theme.Amarelo
import school.sptech.ui.theme.AmareloOpacidade10
import school.sptech.ui.theme.Azul
import school.sptech.ui.theme.AzulOpacidade15
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.Laranja
import school.sptech.ui.theme.LaranjaDourado
import school.sptech.ui.theme.LaranjaOpacidade15
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Verde
import school.sptech.ui.theme.VerdeOpacidade15
import school.sptech.ui.theme.Vermelho
import school.sptech.ui.theme.VermelhoOpacidade15
import school.sptech.ui.theme.fontFamily

class TelaInicial : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            school.sptech.ui.theme.ui.theme.CalencareAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TelaInicial(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CabecalhoInicio(modifier: Modifier = Modifier){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically,
    ) {
        Row(
            modifier = modifier,
            Arrangement.Absolute.Left,
            Alignment.CenterVertically
        ){

            IconButton(onClick = { /*TODO*/ }, modifier = modifier.size(52.dp)) {
                Image(
                    bitmap = ImageBitmap.imageResource(id = R.mipmap.foto_perfil),
                    contentDescription = "Foto de Perfil",
                    modifier = modifier.fillMaxSize()
                )
            }

            Spacer(modifier = modifier.size(16.dp))

            Column {
                Text(
                    text = "Patricia Dias",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    letterSpacing = -0.5.sp,
                    lineHeight = 15.sp,
                    color = RoxoNubank
                )

                Spacer(modifier = modifier.size(4.dp))

                Text(
                    text = "Studio Patricia Dias",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.5.sp,
                    letterSpacing = -0.5.sp,
                    lineHeight = 15.sp,
                    color = Cinza
                )
            }
        }


        Row {
            Icon(
                bitmap = ImageBitmap.imageResource(id = R.mipmap.notificacao),
                "I",
                modifier = Modifier.size(32.dp),
                tint = Preto
            )
        }
    }
}

@Composable
fun TelaInicial(modifier:Modifier = Modifier){
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
    Column(modifier = modifier
        .padding(horizontal = 24.dp, vertical = 12.dp)
        .verticalScroll(state = ScrollState(1))) {
        CabecalhoInicio()
        Spacer(modifier = Modifier.size(21.dp))
        BoxKpisEstoque(
            qtdProdutosEstoqueAlto = 25,
            qtdProdutosSemEstoque = 1,
            qtdProdutosRepostosNoDia = 0,
            qtdProdutosEstoqueBaixo = 5
        )
        Spacer(modifier = Modifier.size(21.dp))
        BoxProdutos(produtos = listaProdutos, titulo = stringResource(id = R.string.produtosComQuantidadeBaixa))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InicioPreview(){
    CalencareAppTheme {
        TelaInicial()
    }
}

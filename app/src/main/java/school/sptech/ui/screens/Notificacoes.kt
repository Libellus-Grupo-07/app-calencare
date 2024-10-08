@file:OptIn(ExperimentalMaterial3Api::class)

package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import school.sptech.R
import school.sptech.ui.components.Background
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.theme.Amarelo
import school.sptech.ui.theme.CalencareAppTheme
import school.sptech.ui.theme.Laranja
import school.sptech.ui.theme.Preto
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.Vermelho

class Notificacoes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                TelaNotificacoes(navController = rememberNavController())
            }
        }
    }
}

@Composable
fun TelaNotificacoes(navController: NavController, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopBarVoltar(
                    navController = navController,
                    titulo = stringResource(R.string.notificacoes)
                )
            }

        ) { innerPadding ->
            Background()

            Column(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                        .padding(4.dp)
                ) {
                    NotificacaoBloco("05/09/2024 21:59", "Esmalte Azul Metálico Risqué", 3)
                    NotificacaoBloco("05/09/2024 21:55", "Shampoo Mais Lisos Wella", 0)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    NotificacaoBloco("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                }
            }
        }
    }
}


@Composable
fun MultiStyleText(
    text1: String, color1: Color,
    text2: String, color2: Color,
    text3: String, color3: Color,
    text4: String, color4: Color
) {
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(color = color1)) {
            append(text1)
        }
        withStyle(style = SpanStyle(color = color2, fontWeight = FontWeight.SemiBold)) {
            append(text2)
        }
        withStyle(style = SpanStyle(color = color3)) {
            append(text3)
        }
        withStyle(style = SpanStyle(color = color4, fontWeight = FontWeight.SemiBold)) {
            append(text4)
        }
    })
}

@Composable
fun NotificacaoBloco(dtHora: String, nomeProduto: String, qntdProduto: Int) {
    var cor = Color.Black
    var img = R.mipmap.orangealert
    var textoAlerta = ""

    if (qntdProduto in 11..20) {
        cor = Amarelo
        img = R.mipmap.yellowalert
        textoAlerta = stringResource(R.string.estoqueBaixo)
    } else if (qntdProduto in 1..10) {
        cor = Laranja
        img = R.mipmap.orangealert
        textoAlerta = stringResource(R.string.quaseSemEstoque)
    } else if (qntdProduto == 0) {
        cor = Vermelho
        img = R.mipmap.redalert
        textoAlerta = stringResource(R.string.semEstoque)
    }

    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = dtHora,
                    color = Color(88, 88, 88, 255),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Image(
                        painter = painterResource(id = img),
                        contentDescription = "Ícone de indicação de alerta",
                        Modifier.size(16.dp)
                    )
                }

                Column {
                    Text(
                        text = textoAlerta,
                        color = cor,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                }
            }
        }

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 25.dp, start = 25.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MultiStyleText(
                    stringResource(R.string.oProduto), Preto,
                    nomeProduto, RoxoNubank,
                    stringResource(R.string.situacaoEstoque), Preto,
                    stringResource(R.string.qtdProdutoUnidade, qntdProduto), RoxoNubank
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                color = Color.LightGray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview2() {
    CalencareAppTheme {
        TelaNotificacoes(rememberNavController())
    }
}
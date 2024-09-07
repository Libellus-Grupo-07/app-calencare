@file:OptIn(ExperimentalMaterial3Api::class)

package school.sptech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.sptech.ui.theme.CalencareAppTheme

class Notificacoes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                //val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
// .nestedScroll(scrollBehavior.nestedScrollConnection)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(248,248,248,0),
                                titleContentColor = Color(0,0,0,255),
//                                containerColor = MaterialTheme.colorScheme.primaryContainer,
//                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("Notificações",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { /* do something */ }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Localized description",
                                        tint = Color(0,0,0,255)
                                    )
                                }
                            },
                            //scrollBehavior = scrollBehavior
                        )
                    },

                ) { innerPadding ->
                    Notificacoes(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Notificacoes(name: String, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize().background(Color(248,248,248,255))) {
        Image(painter = painterResource(id = R.drawable.bg),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column (modifier = modifier.verticalScroll(rememberScrollState())) {
//            Row (
//                modifier = modifier.padding(25.dp),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Column (
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(
//                        painter = painterResource(id = R.mipmap.leftarrow),
//                        contentDescription = "Ícone voltar",
//                        modifier = modifier.size(25.dp)
//                    )
//                }
//
//                Column (
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = modifier.padding(start = 100.dp)
//                    //modifier = modifier.weight(1f)
//                    //modifier = modifier.weight(0.5f)
//                ) {
//                    Text(text = "Notificações",
//                        color = Color(0,0,0,255),
//                        style = TextStyle(
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.SemiBold,
//                        )
//                    )
//                }
//            }

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

@Composable
fun MultiStyleText(text1: String, color1: Color,
                   text2: String, color2: Color,
                   text3: String, color3: Color,
                   text4: String, color4: Color) {
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
fun NotificacaoBloco(dtHora:String, nomeProduto:String, qntdProduto:Int) {
    var cor = Color.Black
    var img = R.mipmap.orangealert
    var textoAlerta = ""

    if (qntdProduto in 11..20) {
        cor = Color(210, 181, 26, 255)
        img = R.mipmap.yellowalert
        textoAlerta = "  ESTOQUE BAIXO"
    } else if (qntdProduto in 1..10) {
        cor = Color(232, 122, 21, 255)
        img = R.mipmap.orangealert
        textoAlerta = "  QUASE SEM ESTOQUE"
    } else if (qntdProduto == 0) {
        cor = Color.Red
        img = R.mipmap.redalert
        textoAlerta = "  SEM ESTOQUE"
    }

    Column (modifier = Modifier) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

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
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 25.dp, start = 25.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MultiStyleText("O produto ", Color(0, 0, 0, 255),
                    nomeProduto, Color(159, 53, 240, 255),
                    " está com estoque em ", Color(0, 0, 0, 255),
                    "$qntdProduto unidades.", Color(159, 53, 240, 255) )
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
        Notificacoes("Android")
    }
}
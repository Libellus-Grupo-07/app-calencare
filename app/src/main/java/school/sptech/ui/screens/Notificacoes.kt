@file:OptIn(ExperimentalMaterial3Api::class)

package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import school.sptech.R
import school.sptech.ui.components.Background
import school.sptech.ui.components.CardNotificacoes
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.theme.CalencareAppTheme

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
                    CardNotificacoes("05/09/2024 21:59", "Esmalte Azul Metálico Risqué", 3)
                    CardNotificacoes("05/09/2024 21:55", "Shampoo Mais Lisos Wella", 0)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                    CardNotificacoes("05/09/2024 21:21", "Esmalte Azul Metálico Risqué", 15)
                }
            }
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
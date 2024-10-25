package school.sptech.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import school.sptech.R
import school.sptech.navigation.NavigationGraph
import school.sptech.ui.theme.CalencareAppTheme

class TelaSplash : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                val navController = rememberNavController()
                NavigationGraph(
                    navController = navController,
                    onBottomBarVisibleChanged = { isVisible -> false }
                )
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        // Inicia a animação de saída e navega para a tela de login
        delay(3000) // 3 segundos de delay
        visible = false // Inicia a animação de saída
        // O delay antes da navegação foi removido para que a tela de login apareça imediatamente após a animação
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(3000)) + slideInVertically(initialOffsetY = { it / 2 }),
        exit = fadeOut(animationSpec = tween(3000)) + slideOutVertically(targetOffsetY = { -it / 2 })
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .then(modifier)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.mipmap.imagem_logo_calencare),
                    contentDescription = "Logo Calencare",
                    modifier = Modifier.size(350.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.mipmap.logo_libellus),
                    contentDescription = "Logo Libellus",
                    modifier = Modifier.size(150.dp)
                )
            }
        }
    }
}


package school.sptech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import school.sptech.ui.components.BottomBar
import school.sptech.bottom.navigation.NavigationGraph
import school.sptech.ui.components.Background
import school.sptech.ui.components.TopBarVoltar
import school.sptech.ui.theme.BrancoFundo
import school.sptech.ui.theme.CalencareAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalencareAppTheme {
                val navController = rememberNavController()
                var buttonVisible by remember { mutableStateOf(true) }
                var existTopBar by remember { mutableStateOf(false) }
                var tituloTopBar by remember { mutableStateOf("") }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BrancoFundo),
                    bottomBar = {
                        if (buttonVisible) {
                            BottomBar(
                                navController = navController,
                                state = buttonVisible,
                                modifier = Modifier
                            )
                        }
                    },
                ) { innerPadding ->
                    Box(modifier = Modifier
                        .padding(
                                top = 0.dp,
                                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                                bottom = innerPadding.calculateBottomPadding(),
                        )) {
                        NavigationGraph(
                            navController = navController,
                            onBottomBarVisibleChanged = { isVisible ->
                                buttonVisible = isVisible
                            },
                            onTopBarVisibleChanged = { exist ->
                                existTopBar = exist
                            },
                            onTitleTopBarChanged = { titulo ->
                                tituloTopBar = titulo
                            }
                        )
                    }
                }
            }
        }
    }
}
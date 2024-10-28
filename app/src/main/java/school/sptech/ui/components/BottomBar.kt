package school.sptech.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import school.sptech.navigation.NavBar
import school.sptech.ui.theme.Branco
import school.sptech.ui.theme.Cinza
import school.sptech.ui.theme.RoxoNubank
import school.sptech.ui.theme.RoxoNubankOpacidade15
import school.sptech.ui.theme.RoxoNubankOpacidade7
import school.sptech.ui.theme.fontFamilyPoppins

@Composable
fun BottomBar(
    navController: NavHostController,
    state: Boolean,
    modifier: Modifier = Modifier
){
    val telas = listOf(
        NavBar.Inicio,
        NavBar.Estoque,
        NavBar.Financas,
        NavBar.Dashboard,
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Branco,
    ) {
        val navBackStacKEntry by navController.currentBackStackEntryAsState()
        val rotaAtual = navBackStacKEntry?.destination?.route

        telas.forEach{ tela ->
            NavigationBarItem(
                selected = rotaAtual == tela.route,
                onClick = {
                    if(rotaAtual != tela.route) {
                        navController.navigate(tela.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        bitmap = ImageBitmap.imageResource(tela.icon!!),
                        contentDescription = "Ícone de ${tela.titulo}",
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        text = tela.titulo!!,
                        fontSize = 12.5.sp,
                        letterSpacing = -0.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fontFamilyPoppins
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Cinza,
                    unselectedTextColor = Cinza,
                    selectedIconColor = RoxoNubank,
                    selectedTextColor = RoxoNubank,
                    indicatorColor = RoxoNubankOpacidade7,

                )
            )
        }

    }
}
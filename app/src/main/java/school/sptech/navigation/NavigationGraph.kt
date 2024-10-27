package school.sptech.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import school.sptech.R
import school.sptech.Routes
import school.sptech.helper.PreferencesHelper
import school.sptech.ui.screens.TelaConta
import school.sptech.ui.screens.LoginScreen
import school.sptech.ui.screens.SplashScreen
import school.sptech.ui.screens.TelaAddDespesa
import school.sptech.ui.screens.TelaAdicionarProdutoScreen
import school.sptech.ui.screens.TelaDashboard
import school.sptech.ui.screens.TelaEstoque
import school.sptech.ui.screens.TelaFinancas
import school.sptech.ui.screens.TelaInformacoesProdutoScreen
import school.sptech.ui.screens.TelaInicio
import school.sptech.ui.screens.TelaNotificacoes

@Composable
fun NavigationGraph(
    preferencesHelper:PreferencesHelper? = null,
    navController: NavHostController,
    onBottomBarVisibleChanged: (Boolean) -> Unit,
) {
    NavHost(navController, startDestination = Routes.Splash.route) {
        composable(Routes.Splash.route) {
            onBottomBarVisibleChanged(false)
            SplashScreen(
                userLogado = preferencesHelper?.getIdUsuario() != -1,
                navController = navController
            )
        }

        composable(Routes.Login.route) {
            onBottomBarVisibleChanged(false)
            LoginScreen(navController = navController)
        }

        composable(NavBar.Inicio.route) {
            onBottomBarVisibleChanged(true)
            TelaInicio(navController = navController)
        }

        composable(Routes.Notificacoes.route) {
            onBottomBarVisibleChanged(false)
            TelaNotificacoes(navController = navController)
        }

        composable(NavBar.Estoque.route) {
            onBottomBarVisibleChanged(true)
            TelaEstoque(navController = navController)
        }

        composable(NavBar.Financas.route) {
            onBottomBarVisibleChanged(true)
            TelaFinancas(navController = navController)
        }

        composable(NavBar.Dashboard.route) {
            onBottomBarVisibleChanged(true)
            TelaDashboard()
        }

        composable(Routes.AdicionarProduto.route) {
            onBottomBarVisibleChanged(false)
            TelaAdicionarProdutoScreen(navController = navController)
        }

        composable(Routes.AdicionarDespesa.route) {
            onBottomBarVisibleChanged(false)
            TelaAddDespesa(navController = navController)
        }

        composable(Routes.DadosPessoais.route) {
            onBottomBarVisibleChanged(false)
            TelaConta(navController =  navController)
        }

        composable(
            "${Routes.InformacoesProduto.route}/{produtoId}",
            arguments = listOf(navArgument("produtoId") { type = NavType.IntType })
        ) {backStackEntry ->
            onBottomBarVisibleChanged(false)
            TelaInformacoesProdutoScreen(
                navController = navController,
                idProduto = backStackEntry.arguments?.getInt("produtoId") ?: 0
//                idProduto = 1
            )
        }
    }
}
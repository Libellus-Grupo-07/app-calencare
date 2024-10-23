package school.sptech.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import school.sptech.R
import school.sptech.Routes
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
    navController: NavHostController,
    onBottomBarVisibleChanged: (Boolean) -> Unit,
    onTopBarVisibleChanged: (Boolean) -> Unit,
    onTitleTopBarChanged: (String) -> Unit
) {
    NavHost(navController, startDestination = Routes.Splash.route) {
        composable(Routes.Splash.route) {
            onBottomBarVisibleChanged(false)
            onTopBarVisibleChanged(false)
            onTitleTopBarChanged("")
            SplashScreen(navController)
        }

        composable(Routes.Login.route) {
            onBottomBarVisibleChanged(false)
            onTopBarVisibleChanged(false)
            onTitleTopBarChanged("")
            LoginScreen(navController = navController)
        }

        composable(NavBar.Inicio.route) {
            onBottomBarVisibleChanged(true)
            onTopBarVisibleChanged(false)
            onTitleTopBarChanged("")
            TelaInicio(navController = navController)
        }

        composable(Routes.Notificacoes.route) {
            onBottomBarVisibleChanged(false)
            onTopBarVisibleChanged(true)
            onTitleTopBarChanged(stringResource(id = R.string.notificacoes))
            TelaNotificacoes(navController = navController)
        }

        composable(NavBar.Estoque.route) {
            onBottomBarVisibleChanged(true)
            onTopBarVisibleChanged(false)
            TelaEstoque(navController)
        }

        composable(NavBar.Financas.route) {
            onBottomBarVisibleChanged(true)
            onTopBarVisibleChanged(false)
            TelaFinancas(navController)
        }

        composable(NavBar.Dashboard.route) {
            onBottomBarVisibleChanged(true)
            onTopBarVisibleChanged(false)
            TelaDashboard()
        }

        composable(Routes.AdicionarProduto.route) {
            onBottomBarVisibleChanged(false)
            onTopBarVisibleChanged(true)
            TelaAdicionarProdutoScreen(navController)
        }

        composable(Routes.AdicionarDespesa.route) {
            onBottomBarVisibleChanged(false)
            onTopBarVisibleChanged(true)
            TelaAddDespesa(navController)
        }

        composable(Routes.DadosPessoais.route) {
            onBottomBarVisibleChanged(false)
            onTopBarVisibleChanged(false)
            TelaConta(navController =  navController)
        }

        composable(Routes.InformacoesProduto.route) {
            onBottomBarVisibleChanged(false)
            onTopBarVisibleChanged(true)
            TelaInformacoesProdutoScreen(navController = navController)
        }
    }
}
package school.sptech.bottom.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import school.sptech.Routes
import school.sptech.TelaInicial

@Composable
fun NavigationGraph(
    navController:NavHostController,
){
    NavHost(navController, startDestination = Routes.Inicio.route){
        composable(Routes.Inicio.route){
            TelaInicial()
        }

        composable(Routes.Estoque.route){
            TelaInicial()
        }

        composable(Routes.Financas.route){
            TelaInicial()
        }

        composable(Routes.Dashboard.route){
            TelaInicial()
        }
    }
}
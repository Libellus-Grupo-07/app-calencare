package school.sptech.bottom.navbar

import school.sptech.R

sealed class BottomNavBar(
    val route: String,
    val titulo:String? = null,
    val icon: Int? = null
) {

    object Inicio: BottomNavBar(
        route = "inicio",
        titulo = "Inicio",
        icon = R.mipmap.inicio
    )

    object Estoque: BottomNavBar(
        route = "estoque",
        titulo = "Estoque",
        icon = R.mipmap.estoque
    )

    object Financas: BottomNavBar(
        route = "financas",
        titulo = "Finanças",
        icon = R.mipmap.financas
    )

    object Dashboard: BottomNavBar(
        route = "dashboard",
        titulo = "Dashboard",
        icon = R.mipmap.dashboard
    )
}
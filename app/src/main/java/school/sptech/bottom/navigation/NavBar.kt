package school.sptech.bottom.navigation

import school.sptech.R

sealed class NavBar(
    val route: String,
    val titulo:String? = null,
    val icon: Int? = null
) {

    object Inicio: NavBar(
        route = "inicio",
        titulo = "Inicio",
        icon = R.mipmap.inicio
    )

    object Estoque: NavBar(
        route = "estoque",
        titulo = "Estoque",
        icon = R.mipmap.estoque
    )

    object Financas: NavBar(
        route = "financas",
        titulo = "Finanças",
        icon = R.mipmap.financas
    )

    object Dashboard: NavBar(
        route = "dashboard",
        titulo = "Dashboard",
        icon = R.mipmap.dashboard
    )
}
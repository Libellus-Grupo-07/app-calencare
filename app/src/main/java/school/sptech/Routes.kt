package school.sptech

sealed class Routes(val route:String) {
    object Inicio : Routes("inicio")
    object Estoque : Routes("estoque")
    object Financas : Routes("financas")
    object Dashboard : Routes("dashboard")

}
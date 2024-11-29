package school.sptech.di

data class UserSession(
    var idUser: Int = 0,
    var idEmpresa: Int = 0,
    var nome: String = "",
    var nomeEmpresa: String = "",
    var logado: Boolean = false
)
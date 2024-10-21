package school.sptech.data.model

data class Funcionario(
    var id:Int? = null,
    var nome:String? = null,
    var telefone:String? = null,
    var email: String? = null,
    var senha: String? = null,
    var perfilAcesso: String? = null,
    var empresa: Empresa? = null,
    var empresaId: Int? = null
)

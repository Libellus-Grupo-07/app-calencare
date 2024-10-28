package school.sptech.data.model

data class Validade(
    var id:Int? = null,
    var descricao:String? = null,
    var dtValidade:String? = null,
    var dtCriacao:String? = null,
    var produtoId: Int? = null,
    var idProduto: Int? = null,
    var enabledValidade: Boolean? = null
)

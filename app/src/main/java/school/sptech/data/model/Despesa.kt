package school.sptech.data.model

data class Despesa(
    var id:Int? = null,
    var nome: String? = null,
    var observacao:String? = null,
    var valor: String? = "0,00",
    var formaPagamento:String? = null,
    var dtPagamento: String? = null,
    var dtCriacao: String? = null,
    var categoriaDespesaNome: String? = null,
    var categoriaDespesaId: Int? = null,
    var empresaId: Int? = null
)


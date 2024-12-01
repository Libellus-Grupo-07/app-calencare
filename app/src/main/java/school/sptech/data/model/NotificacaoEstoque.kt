package school.sptech.data.model

data class NotificacaoEstoque(
    var id: Int? = null,
    var nomeProduto: String? = null,
    var nivelEstoque: String? = null,
    var quantidade: Int? = null,
    var lido: Int? = null,
    var excluido: Int? = null,
    var dtCriacao: String? = null,
    var dtLeitura: String? = null,
    var dtExclusao: String? = null,
    var idProduto: Int? = null,
)

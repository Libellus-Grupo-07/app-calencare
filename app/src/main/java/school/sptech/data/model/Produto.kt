package school.sptech.data.model

data class Produto(
    var id: Int? = null,
    var nome: String? = null,
    var descricao: String? = null,
    var marca: String? = null,
    var qtdEstoque: Int? = null,
    var categoriaProdutoId: Int? = null,
    var categoriaProdutoNome:String? = null,
    var empresaId:Int? = null,
    var empresa: Empresa? = null,
    var validades: List<Validade>? = null,
)
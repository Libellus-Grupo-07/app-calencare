package school.sptech.data.model

data class Produto(
    var id: Int? = null,
    var empresaId:Int? = null,
    var nome: String? = null,
    var descricao: String? = null,
    var marca: String? = null,
    var qntdTotalEstoque: Int? = null,
    var categoriaProdutoId: Int? = null,
    var categoriaProdutoNome:String? = null,
    var validades: List<Validade>? = null,
)
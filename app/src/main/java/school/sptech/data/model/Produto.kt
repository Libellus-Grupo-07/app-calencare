package school.sptech.data.model

data class Produto(
    val id: Int? = null,
    val nome: String? = null,
    val descricao: String? = null,
    val marca: String? = null,
    val qtdEstoque: Int? = null,
    val catagoriaProdutoId: Int? = null,
    val categoriaProdutoNome:String? = null,
    val empresaId:Int? = null,
    val empresa: Empresa? = null
)
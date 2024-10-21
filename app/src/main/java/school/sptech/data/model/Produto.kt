package school.sptech.data.model

data class Produto(
    val id: Int? = null,
    val nome: String? = null,
    val descricao: String? = null,
    val qtdEstoque: Int? = null,
    val marca: String? = null,
    val categoria: CategoriaProduto? = null,
    val empresa: Empresa? = null
)
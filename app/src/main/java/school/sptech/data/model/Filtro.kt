package school.sptech.data.model

data class Filtro(
    var rangeQtdEstoque: ClosedFloatingPointRange<Float> = 0f..0f,
    var categorias: MutableList<CategoriaProduto> = mutableListOf(),
    var dtReposicao: String = "",
    var dtRetirada: String = "",
    var dtValidade: String = ""
)

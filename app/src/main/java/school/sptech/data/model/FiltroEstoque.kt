package school.sptech.data.model

data class FiltroEstoque(
    var rangeQtdEstoque: ClosedFloatingPointRange<Float> = 0f..0f,
    var categorias: MutableList<String> = mutableListOf(),
    var dtReposicao: String = "",
    var dtRetirada: String = "",
    var dtValidade: String = ""
)

package school.sptech.data.model

data class FiltroDespesa(
    var categorias: MutableList<String> = mutableListOf(),
    var dtPagamento: String = "",
    var formasPagamento: MutableList<String> = mutableListOf(),
    var valorMinimo: Double = 0.0,
    var valorMaximo: Double = 0.0
)

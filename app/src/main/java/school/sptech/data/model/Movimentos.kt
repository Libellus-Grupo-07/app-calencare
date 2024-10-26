package school.sptech.data.model

import java.time.LocalDate

data class Movimentos(
    var dtMovimentos: LocalDate,
    var nome: String,
    var valor: Double,
    var tipoMovimentos: String,
)

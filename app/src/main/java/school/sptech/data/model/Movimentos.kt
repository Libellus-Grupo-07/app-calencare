package school.sptech.data.model

import java.time.LocalDate

data class Movimentos(val dtMovimentos: LocalDate,
                      val nome: String,
                      val valor: Double,
                      val tipoMovimentos:String,)

package school.sptech.data.model

import java.time.LocalDate

data class Despesa(
    val id:Int? = null,
    val nome: String? = null,
    val descricao:String? = null,
    val valor: String? = null,
    val dtCriacao: LocalDate? = null,
    val empresa: Empresa? = null,
    val categoriaDespesa: CategoriaDespesa? = null
)

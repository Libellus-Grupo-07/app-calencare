package school.sptech.data.model

data class Endereco(
    val id:Int? = null,
    val cep:String? = null,
    val logradouro:String? = null,
    val numero:String? = null,
    val complemento: String? = null,
    val bairro: String? = null,
    val cidade: String? = null,
    val uf: String? = null,
    val empresa: Empresa
)

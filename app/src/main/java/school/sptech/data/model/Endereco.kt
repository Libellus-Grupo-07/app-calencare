package school.sptech.data.model

data class Endereco(
    var id:Int? = null,
    var cep:String? = null,
    var logradouro:String? = null,
    var numero:String? = null,
    var complemento: String? = null,
    var bairro: String? = null,
    var localidade: String? = null,
    var uf: String? = null,
    var descricaoEndereco: String? = null,
    var empresa: Empresa? = null
)

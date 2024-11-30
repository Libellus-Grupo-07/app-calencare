package school.sptech.data.model

data class Agendamento(
    var dia: String? = null,
    var horario: String? = null,
    var horarioFinalizacao: String? = null,
    var preco: Double? = null,
    var nomeServico: String? = null,
    var nomeFuncionario: String? = null,
    var nomeCliente: String? = null,
    var descricaoStatus: String? = null
)

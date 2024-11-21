package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import school.sptech.data.model.Agendamento

interface AgendamentoService {
    @GET("/api/agendamentos/historico_agendamentos")
    suspend fun getAgendamentosPorData(
        @Query("empresaId") empresaId:Int,
        @Query("dataInicio") dataInicio:String,
        @Query("dataFim") dataFim:String
    ): Response<List<Agendamento>>
}
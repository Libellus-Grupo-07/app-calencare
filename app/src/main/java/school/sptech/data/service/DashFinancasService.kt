package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import school.sptech.data.model.DashFinancas

interface DashFinancasService {
    @GET("/api/dash-financas/agendamentos-receita/{empresaId}/{ano}/{mes}")
    suspend fun getReceitasPorMesAno(
        @Path("empresaId") empresaId: Int,
        @Path("ano") ano: Int,
        @Path("mes") mes: Int
    ): Response<List<DashFinancas>>

    @GET("/api/dash-financas/agendamentos-despesa/{empresaId}/{ano}/{mes}")
    suspend fun getDespesasPorMesAno(
        @Path("empresaId") empresaId: Int,
        @Path("ano") ano: Int,
        @Path("mes") mes: Int
    ): Response<List<DashFinancas>>

    @GET("/api/dash-financas/agendamentos-lucro/{empresaId}/{ano}/{mes}")
    suspend fun getLucrosPorMesAno(
        @Path("empresaId") empresaId: Int,
        @Path("ano") ano: Int,
        @Path("mes") mes: Int
    ): Response<List<DashFinancas>>
}
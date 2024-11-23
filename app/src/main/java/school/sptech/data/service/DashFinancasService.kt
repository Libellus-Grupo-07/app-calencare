package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import school.sptech.data.model.DashFinancas
import school.sptech.data.model.DashFinancasSemana

interface DashFinancasService {
    @GET("/api/dash-financas/semana/agendamentos-receita/{empresaId}/{ano}/{mes}")
    suspend fun getReceitasPorMesAno(
        @Path("empresaId") empresaId: Int,
        @Path("ano") ano: Int,
        @Path("mes") mes: Int
    ): Response<List<DashFinancas>>

    @GET("/api/dash-financas/semana/despesa/{empresaId}/{ano}/{mes}")
    suspend fun getDespesasPorMesAno(
        @Path("empresaId") empresaId: Int,
        @Path("ano") ano: Int,
        @Path("mes") mes: Int
    ): Response<List<DashFinancas>>

    @GET("/api/dash-financas/semana/agendamentos-lucro/{empresaId}/{ano}/{mes}")
    suspend fun getLucrosPorMesAno(
        @Path("empresaId") empresaId: Int,
        @Path("ano") ano: Int,
        @Path("mes") mes: Int
    ): Response<List<DashFinancas>>

    @GET("/api/dash-financas/dash/{empresaId}/{ano}/{mes}")
    suspend fun getDadosDashboard(
        @Path("empresaId") empresaId: Int,
        @Path("ano") ano: Int,
        @Path("mes") mes: Int
    ): Response<List<List<DashFinancas>>>


}
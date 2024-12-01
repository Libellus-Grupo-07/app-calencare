package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import school.sptech.data.model.Movimentacoes
import school.sptech.data.model.Movimentos

interface FinancasService {
    @GET("/api/financas/{empresaId}/{mes}/{ano}")
    suspend fun getMovimentacoes(
        @Path("empresaId") empresaId: Int,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Response<List<Movimentacoes>>

    @GET("/api/financas/kpi/receitas-mes/{empresaId}/{mes}/{ano}")
    suspend fun getKpiReceitasMes(
        @Path("empresaId") empresaId: Int,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Response<Double>

    @GET("/api/financas/kpi/despesa-mes/{empresaId}/{mes}/{ano}")
    suspend fun getKpiDespesaMes(
        @Path("empresaId") empresaId: Int,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Response<Double>

    @GET("/api/financas/kpi/lucro-mes/{empresaId}/{mes}/{ano}")
    suspend fun getKpiLucroMes(
        @Path("empresaId") empresaId: Int,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Response<Double>

    @GET("/api/financas/kpi/comissao-mes/{empresaId}/{mes}/{ano}")
    suspend fun getKpiComissaoMes(
        @Path("empresaId") empresaId: Int,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Response<Double>
}
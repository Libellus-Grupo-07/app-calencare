package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import school.sptech.data.model.Movimentacoes

interface MovimentacoesService {
    @GET("/api/financas/{empresaId}/{mes}/{ano}")
    suspend fun getMovimentacoes(
        @Path("empresaId") empresaId: Int,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Response<List<Movimentacoes>>
}
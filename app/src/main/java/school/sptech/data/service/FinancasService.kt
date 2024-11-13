package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import school.sptech.data.model.Movimentos

interface FinancasService {
    @GET("/api/financas/{empresaId}/{mes}/{ano}")
    suspend fun getMovimentosDoMes(
        @Path("empresaId") empresaId: Int,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Response<List<Movimentos>>
}
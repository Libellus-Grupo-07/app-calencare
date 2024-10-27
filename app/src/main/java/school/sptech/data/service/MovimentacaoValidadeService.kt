package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import school.sptech.data.model.MovimentacaoValidade

interface MovimentacaoValidadeService {
    @GET("/movimentacao-validade/quantidade/produto/{produtoId}")
    suspend fun getTotalEstoque(@Path("produtoId") produtoId: Int): Response<Int>

    @GET("/movimentacao-validade/quantidade/{validadeId}")
    suspend fun getQuantidadePorValidade(@Path("validadeId") validadeId: Int): Response<Int>

    @POST("/movimentacao-validade")
    suspend fun postMovimentacaoValidade(@Body movimentacaoValidade: MovimentacaoValidade): Response<MovimentacaoValidade>
}
package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import school.sptech.data.model.MovimentacaoValidade

interface MovimentacaoValidadeService {
    @GET("/quantidade/produto/{produtoId}")
    suspend fun getQuantidadeTodasValidades(@Path("produtoId") produtoId:Int): Response<Int>
    @POST
    suspend fun postMovimentacaoValidade(@Body movimentacaoValidade: MovimentacaoValidade): Response<MovimentacaoValidade>
}
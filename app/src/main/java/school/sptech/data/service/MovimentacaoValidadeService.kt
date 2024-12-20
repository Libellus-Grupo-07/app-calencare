package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import school.sptech.data.model.MovimentacaoValidade
import java.time.LocalDate

interface MovimentacaoValidadeService {
    @GET("/api/movimentacao-validade/quantidade/produto/{produtoId}")
    suspend fun getTotalEstoque(@Path("produtoId") produtoId: Int): Response<Int>

    @GET("/api/movimentacao-validade/quantidade/{validadeId}")
    suspend fun getQuantidadePorValidade(@Path("validadeId") validadeId: Int): Response<Int>

    @POST("/api/movimentacao-validade")
    suspend fun postMovimentacaoValidade(@Body movimentacaoValidade: MovimentacaoValidade): Response<MovimentacaoValidade>

    @GET("/api/movimentacao-validade/kpi/produtos-ok/{empresaId}")
    suspend fun getProdutosEstoqueAlto(@Path("empresaId") empresaId: Int): Response<Int>

    @GET("/api/movimentacao-validade/kpi/produtos-baixo/{empresaId}")
    suspend fun getProdutosEstoqueAbaixo(@Path("empresaId") empresaId: Int): Response<Int>

    @GET("/api/movimentacao-validade/kpi/sem-estoque/{empresaId}")
    suspend fun getProdutosSemEstoque(@Path("empresaId") empresaId: Int): Response<Int>

    @GET("/api/movimentacao-validade/kpi/reposicao/{empresaId}/{data}")
    suspend fun getProdutosRepostosNoDia(
        @Path("empresaId") empresaId: Int,
        @Path("data") data: LocalDate = LocalDate.now()
    ): Response<Int>

    @GET("/api/movimentacao-validade/listar-movimentacoes-produto/{empresaId}")
    suspend fun getMovimentacoesPorEmpresa(@Path("empresaId") empresaId: Int): Response<List<MovimentacaoValidade>>
}
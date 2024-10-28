package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import school.sptech.data.model.Produto

interface ProdutoService {
    @GET("/api/produto/{empresaId}/{produtoId}")
    suspend fun getProdutoById(@Path("empresaId") empresaId: Int, @Path("produtoId") produtoId: Int): Response<Produto>
    @GET("/api/produto/{empresaId}/buscar-todos")
    suspend fun getAllProdutosByEmpresaId(@Path("empresaId") empresaId:Int): Response<List<Produto>>
    @POST("/api/produto")
    suspend fun adicionarProduto(@Body produto: Produto): Response<Produto>
    @PUT("/api/produto/{empresaId}/{produtoId}")
    suspend fun atualizarProduto(@Path("empresaId") empresaId: Int, @Path("produtoId") produtoId: Int, @Body produto: Produto): Response<Produto>
}
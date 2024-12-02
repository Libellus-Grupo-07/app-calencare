package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import school.sptech.data.model.Validade

interface ValidadeService {
    @POST("/api/validade")
    suspend fun adicionarValidade(@Body validade: Validade): Response<Validade>

    @GET("/api/validade/produto/{produtoId}")
    suspend fun getValidades(@Path("produtoId") produtoId: Int): Response<List<Validade>>

    @GET("/api/validade/empresa/{empresaId}")
    suspend fun getValidadesEmpresa(@Path("empresaId") empresaId: Int): Response<List<Validade>>
}
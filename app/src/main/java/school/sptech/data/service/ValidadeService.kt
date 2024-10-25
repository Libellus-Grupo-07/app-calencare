package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import school.sptech.data.model.Validade

interface ValidadeService {
    @GET("/produto/{produtoId}")
    suspend fun getValidades(@Path("produtoId") produtoId:Int): Response<List<Validade>>

}
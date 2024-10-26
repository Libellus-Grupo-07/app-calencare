package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.GET
import school.sptech.data.model.CategoriaProduto

interface CategoriaProdutoService {
    @GET("/categoria-produto")
    suspend fun getAllCategoriaProduto(): Response<List<CategoriaProduto>>
}
package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.GET
import school.sptech.data.model.CategoriaDespesa

interface CategoriaDespesaService {
    @GET("/api/categoria-despesa")
    suspend fun getAllCategoriaDespesa(): Response<List<CategoriaDespesa>>
}
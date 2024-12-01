package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import school.sptech.data.model.Despesa

interface DespesaService {
    @GET("/api/despesas/{empresaId}")
    suspend fun getAllDespesas(@Path("empresaId") empresaId: Int): Response<List<Despesa>>

    @GET("/api/despesas/info/{empresaId}/{data}")
    suspend fun getAllDespesasByEmpresaIdAndData(
        @Path("empresaId") empresaId: Int,
        @Path("data") data: String,
    ): Response<List<Despesa>>

    @GET("/api/despesas/{empresaId}/{despesaId}")
    suspend fun getDespesaById(
        @Path("empresaId") empresaId: Int,
        @Path("despesaId") despesaId: Int
    ): Response<Despesa>

    @PUT("/api/despesas/{empresaId}/{despesaId}/{categoriaDespesaId}")
    suspend fun putDespesaById(
        @Path("empresaId") empresaId: Int,
        @Path("despesaId") despesaId: Int,
        @Path("categoriaDespesaId") categoriaDespesaId: Int,
        @Body despesa: Despesa
    ): Response<Despesa>

    @POST("/api/despesas/{empresaId}/{categoriaDespesaId}")
    suspend fun postDespesa(
        @Path("empresaId") empresaId: Int,
        @Path("categoriaDespesaId") categoriaDespesaId: Int,
        @Body despesa: Despesa
    ): Response<Despesa>

    @DELETE("/api/despesas/{empresaId}/{despesaId}")
    suspend fun deleteDespesa(
        @Path("empresaId") empresaId: Int,
        @Path("despesaId") despesaId: Int
    ): Response<Void>

}
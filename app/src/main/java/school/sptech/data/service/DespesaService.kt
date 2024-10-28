package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import school.sptech.data.model.Despesa

interface DespesaService {
    @GET("/api/despesas/info/{empresaId}/{mes}/{ano}")
    suspend fun getAllDespesasByEmpresaIdAndMesAndAno(
        @Path("empresaId") empresaId: Int,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Response<List<Despesa>>

    @GET("/api/despesas/{empresaId}/{despesaId}")
    suspend fun getDespesaById(
        @Path("empresaId") empresaId: Int,
        @Path("despesaId") despesaId: Int
    ): Response<Despesa>


    @GET("/api/despesas/total/{empresaId}/{mes}/{ano}")
    suspend fun getTotalDespesasByEmpresaIdAndMesAndAno(
        @Path("empresaId") empresaId: Int,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Response<Double>

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

}
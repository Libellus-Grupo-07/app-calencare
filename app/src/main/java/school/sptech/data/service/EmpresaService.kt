package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import school.sptech.data.model.Empresa

interface EmpresaService {
    @GET("/api/empresas/{id}")
    suspend fun getEmpresa(@Path("id") id: Int): Response<Empresa>
    @PUT("/api/empresas/{id}")
    suspend fun atualizarEmpresa(@Path("id") id: Int, @Body empresa: Empresa): Response<Empresa>
}

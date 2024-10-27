package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import school.sptech.data.model.Empresa

interface EmpresaService {
    @GET("/empresas/{id}")
    suspend fun getEmpresa(@Path("id") id: Int): Response<Empresa>
}

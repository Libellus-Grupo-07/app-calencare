package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import school.sptech.data.model.Funcionario

interface FuncionarioService {
    @POST("/funcionarios/login")
    suspend fun login(@Body funcionario: Funcionario): Response<Funcionario>
    @GET("/funcionarios/{id}")
    suspend fun getFuncionario(@Path("id") id:Int): Response<Funcionario>
    @PUT("/funcionarios/{id}")
    suspend fun putFuncionario(@Path("id") id:Int, @Body funcionario: Funcionario): Response<Funcionario>
}
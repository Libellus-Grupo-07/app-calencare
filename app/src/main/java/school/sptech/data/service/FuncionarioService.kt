package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import school.sptech.data.model.Funcionario

interface FuncionarioService {
    //@POST("/login")
    //suspend fun login(@Body funcionario: Funcionario): Response<Funcionario>
    @GET("/funcionario/{id}")
    suspend fun getFuncionario(@Path("id") id:Int): Response<Funcionario>
}
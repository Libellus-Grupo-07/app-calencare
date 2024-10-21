package school.sptech.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import school.sptech.data.service.EmpresaService
import school.sptech.data.service.FuncionarioService

object RetrofitService {
//    private val BASE_URL_API = "http://localhost:8080"
    private val BASE_URL_API = "https://6715378f33bc2bfe40b9caae.mockapi.io/api/v1/"

    fun getClientEmpresa(): EmpresaService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmpresaService::class.java)

        return client
    }

    fun getClientFuncionario(): FuncionarioService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FuncionarioService::class.java)

        return client
    }
}
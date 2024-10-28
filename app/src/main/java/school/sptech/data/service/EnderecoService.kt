package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import school.sptech.data.model.Endereco

interface EnderecoService {
    @GET("/api/enderecos/empresa/{empresaId}")
    suspend fun getEnderecoByEmpresaId(@Path("empresaId") empresaId: Int): Response<Endereco>

    @POST("/api/enderecos/address/{cep}")
    suspend fun getEnderecoByCep(@Path("cep") cep: String): Response<Endereco>

    @PUT("/api/enderecos/{enderecoId}")
    suspend fun putEndereco(
        @Path("enderecoId") enderecoId: Int,
        @Body endereco: Endereco
    ): Response<Endereco>
}
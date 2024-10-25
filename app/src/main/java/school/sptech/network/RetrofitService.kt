package school.sptech.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import school.sptech.data.service.DespesaService
import school.sptech.data.service.EmpresaService
import school.sptech.data.service.FuncionarioService
import school.sptech.data.service.MovimentacaoValidadeService
import school.sptech.data.service.ProdutoService
import school.sptech.data.service.ValidadeService

object RetrofitService {
//    private val BASE_URL_API = "http://localhost:8080/api"
    private val BASE_URL_API = "https://6715378f33bc2bfe40b9caae.mockapi.io/api/v1/"
    private val BASE_URL_API_EMPRESA = "$BASE_URL_API/empresa"
    private val BASE_URL_API_FUNCIONARIO = "$BASE_URL_API/funcionario"
    private val BASE_URL_API_PRODUTO = "$BASE_URL_API/produto"
    private val BASE_URL_API_CATEGORIA_PRODUTO = "$BASE_URL_API/categoria-produto"
    private val BASE_URL_API_VALIDADE = "$BASE_URL_API/validade"
    private val BASE_URL_API_MOVIMENTACAO = "$BASE_URL_API/movimentacao-validade"
    private val BASE_URL_API_DESPESA = "$BASE_URL_API/despesas"
    private val BASE_URL_API_CATEGORIA_DESPESA = "$BASE_URL_API/categoria-despesa"

    fun getClientEmpresa(): EmpresaService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API_EMPRESA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmpresaService::class.java)

        return client
    }

    fun getClientFuncionario(): FuncionarioService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API_FUNCIONARIO)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FuncionarioService::class.java)

        return client
    }

    fun getClientProduto(): ProdutoService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API_PRODUTO)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProdutoService::class.java)

        return client
    }

    fun getClientValidade(): ValidadeService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API_VALIDADE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ValidadeService::class.java)

        return client
    }

    fun getClientMovimentacaoValidade(): MovimentacaoValidadeService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API_MOVIMENTACAO)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovimentacaoValidadeService::class.java)

        return client
    }

    fun getClientDespesa(): DespesaService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API_DESPESA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DespesaService::class.java)

        return client
    }
}
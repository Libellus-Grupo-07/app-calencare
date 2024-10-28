package school.sptech.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import school.sptech.data.service.CategoriaDespesaService
import school.sptech.data.service.CategoriaProdutoService
import school.sptech.data.service.DespesaService
import school.sptech.data.service.EmpresaService
import school.sptech.data.service.EnderecoService
import school.sptech.data.service.MovimentacoesService
import school.sptech.data.service.FuncionarioService
import school.sptech.data.service.MovimentacaoValidadeService
import school.sptech.data.service.ProdutoService
import school.sptech.data.service.ValidadeService

object RetrofitService{
    private val ipAws = "54.243.135.89"
    private val BASE_URL_API = "http://$ipAws"
//    private val BASE_URL_API = "https://6715378f33bc2bfe40b9caae.mockapi.io/api/v1/"

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

    fun getClientProduto(): ProdutoService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProdutoService::class.java)

        return client
    }

    fun getClientCategoriaProduto(): CategoriaProdutoService{
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoriaProdutoService::class.java)
        return client
    }

    fun getClientValidade(): ValidadeService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ValidadeService::class.java)

        return client
    }

    fun getClientMovimentacaoValidade(): MovimentacaoValidadeService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovimentacaoValidadeService::class.java)

        return client
    }

    fun getClientDespesa(): DespesaService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DespesaService::class.java)

        return client
    }

    fun getClientCategoriaDespesa(): CategoriaDespesaService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoriaDespesaService::class.java)

        return client
    }

    fun getClientEndereco(): EnderecoService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EnderecoService::class.java)

        return client
    }

    fun getClientFinancas(): MovimentacoesService {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovimentacoesService::class.java)

        return client
    }

}
package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.Endereco
import school.sptech.data.service.EnderecoService
import school.sptech.network.RetrofitService

class EnderecoViewModel : ViewModel() {
    private val enderecoService: EnderecoService
    var endereco by mutableStateOf(Endereco())
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    init {
        enderecoService = RetrofitService.getClientEndereco()
    }

    fun getEndereco(empresaId: Int) {
        GlobalScope.launch {
            try {
                var response = enderecoService.getEnderecoByEmpresaId(empresaId = empresaId)

                if (response.isSuccessful && response.body() != null) {
                    endereco = response.body()!!
                    deuErro = false
                    erro = ""
                } else {
                    Log.e(
                        "api",
                        "Erro ao tentar buscar endereco ${response.errorBody()?.string()}"
                    )
                    deuErro = true
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao tentar buscar endereco", ex)
                deuErro = true
            }
        }

    }

    fun getEnderecoViaCep() : Endereco {
        buscarEnderecoPorCep()
        return endereco
    }

    //    fun buscarEnderecoPorCep(abc:String) {
    fun buscarEnderecoPorCep(){
        GlobalScope.launch {
            try {
                var response = enderecoService.getEnderecoByCep(endereco.cep!!)

                if (response.isSuccessful) {
                    val novoEndereco = Endereco(
                        id = endereco.id,
                        logradouro = response.body()?.logradouro,
                        bairro = response.body()?.bairro,
                        localidade = response.body()?.localidade,
                        uf = response.body()?.uf,
                        complemento = response.body()?.complemento,
                        cep = response.body()?.cep,
                        numero = endereco.numero,
                        descricaoEndereco = endereco.descricaoEndereco,
                        empresa = endereco.empresa
                    )
                    endereco = novoEndereco
                    deuErro = false
                    erro = ""
                } else {
                    Log.e(
                        "api",
                        "Erro ao tentar buscar endereco ${response.errorBody()?.string()}"
                    )
                    deuErro = true
                    erro = response.message() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao tentar buscar endereco", ex)
                deuErro = true
            }
        }
    }

    fun atualizarEndereco() {
        GlobalScope.launch {
            try {
                var response = enderecoService.putEndereco(endereco.id!!, endereco)

                if (response.isSuccessful) {
                    endereco = response.body()!!
                    deuErro = false
                    erro = "Endereço atualizado com sucesso!"
                } else {
                    Log.e(
                        "api",
                        "Erro ao tentar atualizar endereco ${response.errorBody()?.string()}"
                    )
                    deuErro = true
                    erro = response.message() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao tentar atualizar endereco", ex)
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }
}
package school.sptech.ui.viewModel

import android.util.Log
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
        if (endereco.id == null) {
            GlobalScope.launch {
                try {
                    var response = enderecoService.getEnderecoByEmpresaId(empresaId = empresaId)

                    if (response.isSuccessful && response.body() != null) {
                        endereco = response.body()!!
                        deuErro = false
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
    }

//    fun buscarEnderecoPorCep(abc:String) {
    fun buscarEnderecoPorCep() {
        GlobalScope.launch {
            try {
                var response = enderecoService.getEnderecoByCep(endereco.cep!!)

                if (response.isSuccessful && response.body() != null) {
                    endereco = response.body()!!
                    deuErro = false
                    erro = "DEU CERTO"
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
}
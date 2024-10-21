package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.Funcionario
import school.sptech.data.service.FuncionarioService
import school.sptech.network.RetrofitService

class UsuarioViewModel : ViewModel() {
    private val usuarioService: FuncionarioService
    var usuario by mutableStateOf(Funcionario())
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    init {
        usuarioService = RetrofitService.getClientFuncionario()
    }

//    fun logar(){
//        GlobalScope.launch {
//            try {
//                var response = usuarioService.login(usuario)
//
//                if(response.isSuccessful && response.body() != null){
//                    usuario = response.body()!!
//                    deuErro = false
//                } else {
//                    Log.e("api", "Erro ao tentar logar ${response.errorBody()?.string()}")
//                    deuErro = true
//                }
//            } catch (ex: Exception){
//                Log.e("api", "Erro ao tentar logar", ex)
//                deuErro = true
//            }
//        }
//    }

    fun getFuncionario(id: Int){
        if(usuario.id == null){
            GlobalScope.launch {
                try {
                    val response = usuarioService.getFuncionario(id)

                    if(response.isSuccessful && response.body() != null){
                        usuario = response.body()!!
                        deuErro = false
                    } else {
                        Log.e("api", "Erro ao tentar buscar funcionario ${response.errorBody()?.string()}")
                        erro = response.errorBody()?.string() ?: "Erro desconhecido"
                        deuErro = true
                    }
                } catch (ex: Exception){
                    Log.e("api", "Erro ao tentar buscar funcionario", ex)
                    deuErro = true
                    erro = ex.message ?: "Erro desconhecido"
                }
            }
        }

    }
}
package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.Empresa
import school.sptech.data.service.EmpresaService
import school.sptech.network.RetrofitService

class EmpresaViewModel : ViewModel() {
    private val empresaService: EmpresaService
    var empresa by mutableStateOf(Empresa())
    var deuErro by mutableStateOf(false)

    init {
        empresaService = RetrofitService.getClientEmpresa()
    }

    fun getEmpresa(id: Int){
        if(empresa.id == null){
            GlobalScope.launch {
                try {
                    var response = empresaService.getEmpresa(id)

                    if(response.isSuccessful && response.body() != null){
                        empresa = response.body()!!
                        deuErro = false
                    } else {
                        Log.e("api", "Erro ao tentar buscar empresa ${response.errorBody()?.string()}")
                        deuErro = true
                    }
                } catch (ex: Exception){
                    Log.e("api", "Erro ao tentar buscar empresa", ex)
                    deuErro = true
                }
            }
        }
    }

}
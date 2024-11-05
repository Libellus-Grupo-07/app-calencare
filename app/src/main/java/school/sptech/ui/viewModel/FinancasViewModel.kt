package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.Movimentos
import school.sptech.data.service.FinancasService
import school.sptech.network.RetrofitService

class FinancasViewModel : ViewModel() {
    private val financasService: FinancasService
    val movimentos = mutableStateListOf<Movimentos>()
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    init {
        financasService = RetrofitService.getClientFinancas()
    }

    fun getMovimentosDoMes(empresaId: Int, mes: Int, ano: Int){
        GlobalScope.launch {
            try {
                val response = financasService.getMovimentosDoMes(empresaId, mes, ano)

                if(response.isSuccessful){
                    movimentos.clear()
                    movimentos.addAll(response.body() ?: listOf())
                    deuErro = false
                    erro = "Consultado com sucesso!"
                } else {
                    Log.e("api", response.errorBody().toString())
                    deuErro = true
                    erro = response.errorBody().toString()
                }

            } catch (ex: Exception) {
                Log.e("api", ex.message.toString())
                deuErro = true
                erro = ex.message.toString()
            }
        }
    }
}
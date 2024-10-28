package school.sptech.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.Financas
import school.sptech.data.service.FinancasService
import school.sptech.network.RetrofitService

class FinancasViewModel : ViewModel() {
    private val financasService:FinancasService
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")
    var financas = mutableListOf<Financas>()

    init {
        financasService = RetrofitService.getClientFinancas()
    }

    fun getFinancas(empresaId: Int, mes: Int, ano: Int) : List<Financas> {
        getFinancasByMes(empresaId, mes, ano)
        return financas
    }

    private fun getFinancasByMes(empresaId: Int, mes: Int, ano: Int) {
        GlobalScope.launch {
            try {
                val response = financasService.getFinancas(empresaId, mes, ano)
                if (response.isSuccessful) {
                    financas.clear()
                    financas.addAll(response.body() ?: listOf())
                    deuErro = false
                } else {

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
package school.sptech.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.Financas
import school.sptech.data.service.MovimentacoesService
import school.sptech.network.RetrofitService

class MovimentacoesViewModel : ViewModel() {
    private val movimentacoesService:MovimentacoesService
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")
    var financas = mutableListOf<Financas>()

    init {
        movimentacoesService = RetrofitService.getClientFinancas()
    }

    fun getFinancas(empresaId: Int, mes: Int, ano: Int) : List<Financas> {
        getFinancasByMes(empresaId, mes, ano)
        return financas
    }

    private fun getFinancasByMes(empresaId: Int, mes: Int, ano: Int) {
        GlobalScope.launch {
            try {
                val response = movimentacoesService.getFinancas(empresaId, mes, ano)
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
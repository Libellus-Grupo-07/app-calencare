package school.sptech.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.Movimentacoes
import school.sptech.data.service.MovimentacoesService
import school.sptech.dataStoreRepository
import school.sptech.network.RetrofitService

class MovimentacoesViewModel : ViewModel() {
    private val movimentacoesService:MovimentacoesService
    private var empresaId:Int = 0
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")
    var movimentacoes = mutableListOf<Movimentacoes>()

    init {
        movimentacoesService = RetrofitService.getClientMovimentacoes()

        GlobalScope.launch {
            empresaId = dataStoreRepository.getEmpresaId()
        }
    }

    fun getMovimentacoes(mes: Int, ano: Int) : List<Movimentacoes> {
        getMovimentacoesByMes(mes, ano)
        return movimentacoes
    }

    private fun getMovimentacoesByMes(mes: Int, ano: Int) {
        GlobalScope.launch {
            try {
                val response = movimentacoesService.getMovimentacoes(empresaId, mes, ano)
                if (response.isSuccessful) {
                    movimentacoes.clear()
                    movimentacoes.addAll(response.body() ?: listOf())
                    deuErro = false
                } else {

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
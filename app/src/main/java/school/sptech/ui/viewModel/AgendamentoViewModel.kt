package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.Agendamento
import school.sptech.data.service.AgendamentoService
import school.sptech.dataStoreRepository
import school.sptech.network.RetrofitService

class AgendamentoViewModel : ViewModel() {
    private val agendamentoService: AgendamentoService
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")
    var agendamentos = mutableListOf<Agendamento>()

    init {
        agendamentoService = RetrofitService.getClientAgendamento()
    }

    fun getListaAgendamentos(): List<Agendamento> {
        return agendamentos.toList().filter { it.descricaoStatus == "Finalizado" }
            .sortedBy { it.horario }
    }

    fun getAgendamentosPorData(dataInicio: String, dataFim: String) {
        GlobalScope.launch {
            try {
                val response = agendamentoService.getAgendamentosPorData(
                    dataStoreRepository.getEmpresaId(),
                    dataInicio,
                    dataFim
                )

                if (response.isSuccessful) {
                    agendamentos.clear()
                    agendamentos.addAll(response.body() ?: listOf())
                    deuErro = false
                    erro = ""
                } else {
                    Log.e("api", response.errorBody().toString())
                    deuErro = true
                    erro = response.errorBody().toString()
                }
            } catch (e: Exception) {
                Log.e("api", e.message.toString())
                deuErro = true
                erro = e.message.toString()
            }
        }
    }

}
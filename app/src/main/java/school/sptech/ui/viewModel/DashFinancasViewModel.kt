package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.DashFinancas
import school.sptech.data.service.DashFinancasService
import school.sptech.dataStoreRepository
import school.sptech.network.RetrofitService

class DashFinancasViewModel : ViewModel() {
    private val dashFinancasService: DashFinancasService
    private var empresaId:Int = 0
    var labels = mutableStateListOf<String>()
    var receitas = mutableStateListOf<DashFinancas>()
    var despesas = mutableStateListOf<DashFinancas>()
    var lucros = mutableStateListOf<DashFinancas>()
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    init {
        dashFinancasService = RetrofitService.getClientDashFinancas()

        GlobalScope.launch {
            empresaId = dataStoreRepository.getEmpresaId()
        }
    }

    fun getDadosDashPorMesAno(mes:Int, ano:Int){
        GlobalScope.launch {
            try {
                val response = dashFinancasService.getDadosDashboard(
                    empresaId = empresaId,
                    ano = ano,
                    mes = mes
                )

                if(response.isSuccessful){
                    val receitasVetor = response.body()?.get(0) ?: listOf()
                    receitas.clear()
                    receitas.addAll(receitasVetor.toList())

                    val lucrosVetor = response.body()?.get(1) ?: listOf()
                    lucros.clear()
                    lucros.addAll(lucrosVetor.toList())

                    val despesasVetor = response.body()?.get(2) ?: listOf()
                    despesas.clear()
                    despesas.addAll(despesasVetor.toList())

                    labels.clear()
                    for (i in 0 until receitas.size){
                        labels.add("Semana ${i + 1}")
                    }

                    deuErro = false
                    erro = "sucess"
                } else {
                    Log.e("api", response.errorBody().toString())
                    deuErro = true
                    erro = response.errorBody().toString()
                }
            } catch (ex: Exception){
                Log.e("api", ex.message.toString())
                deuErro = true
                erro = ex.message.toString()
            }
        }
    }
}
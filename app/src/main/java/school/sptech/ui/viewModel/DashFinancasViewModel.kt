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
    var vetorDash = mutableStateListOf<List<DashFinancas>>()
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

    fun getReceitasPorMesAno(mes:Int, ano:Int){
        GlobalScope.launch {
            try {
                val response = dashFinancasService.getReceitasPorMesAno(
                    empresaId = empresaId,
                    ano = ano,
                    mes = mes
                )

                if(response.isSuccessful){
                    receitas.clear()
                    receitas.addAll(response.body() ?: listOf())
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

    fun getDespesasPorMesAno(mes:Int, ano:Int){
        GlobalScope.launch {
            try {
                val response = dashFinancasService.getDespesasPorMesAno(
                    empresaId = empresaId,
                    ano = ano,
                    mes = mes
                )

                if(response.isSuccessful){
                    despesas.clear()
                    despesas.addAll(response.body() ?: listOf())
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

    fun getLucrosPorMesAno( mes:Int, ano:Int){
        GlobalScope.launch {
            try {
                val response = dashFinancasService.getLucrosPorMesAno(
                    empresaId = empresaId,
                    ano = ano,
                    mes = mes
                )

                if(response.isSuccessful){
                    lucros.clear()
                    lucros.addAll(response.body() ?: listOf())
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
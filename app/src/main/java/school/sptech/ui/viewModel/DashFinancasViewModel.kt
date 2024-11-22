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
import school.sptech.network.RetrofitService
import school.sptech.preferencesHelper
import java.time.LocalDate

class DashFinancasViewModel : ViewModel() {
    private val dashFinancasService: DashFinancasService
    private val empresaId = preferencesHelper.getIdEmpresa()
    var receitas = mutableStateListOf<DashFinancas>()
    var despesas = mutableStateListOf<DashFinancas>()
    var lucros = mutableStateListOf<DashFinancas>()
    var mes by mutableStateOf(LocalDate.now().monthValue)
    var ano by mutableStateOf(LocalDate.now().year)
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    init {
        dashFinancasService = RetrofitService.getClientDashFinancas()
    }

    fun getReceitasPorMesAno(){
        GlobalScope.launch {
            try {
                val response = dashFinancasService.getReceitasPorMesAno(
                    empresaId = empresaId,
                    ano = mes,
                    mes = ano
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

    fun getDespesasPorMesAno(){
        GlobalScope.launch {
            try {
                val response = dashFinancasService.getDespesasPorMesAno(
                    empresaId = empresaId,
                    ano = mes,
                    mes = ano
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

    fun getLucrosPorMesAno(){
        GlobalScope.launch {
            try {
                val response = dashFinancasService.getLucrosPorMesAno(
                    empresaId = empresaId,
                    ano = mes,
                    mes = ano
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
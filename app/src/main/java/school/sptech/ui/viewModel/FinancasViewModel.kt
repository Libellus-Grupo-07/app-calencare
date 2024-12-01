package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.Movimentacoes
import school.sptech.data.model.Movimentos
import school.sptech.data.service.FinancasService
import school.sptech.dataStoreRepository
import school.sptech.network.RetrofitService

class FinancasViewModel : ViewModel() {
    private val financasService: FinancasService
    val movimentacoes = mutableStateListOf<Movimentacoes>()
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")
    var totalReceitas by mutableStateOf(0.0)
    var totalDespesas by mutableStateOf(0.0)
    var totalLucro by mutableStateOf(0.0)
    var totalComissoes by mutableStateOf(0.0)

    init {
        financasService = RetrofitService.getClientFinancas()
    }

    fun getMovimentacoes(mes: Int, ano: Int): List<Movimentacoes> {
        getMovimentacoesByMes(mes, ano)
        return movimentacoes.toList()
    }

    private fun getMovimentacoesByMes(mes: Int, ano: Int) {
        GlobalScope.launch {
            try {
                val response =
                    financasService.getMovimentacoes(
                        empresaId = dataStoreRepository.getEmpresaId(),
                        mes = mes,
                        ano = ano
                    )

                if (response.isSuccessful) {
                    movimentacoes.clear()
                    movimentacoes.addAll(response.body() ?: listOf())
                    deuErro = false
                } else {
                    Log.e(
                        "api",
                        "Erro ao buscar movimentações => ${response.errorBody()?.string()}"
                    )
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (e: Exception) {
                Log.e("api", "Erro ao buscar movimentações => ${e.message}")
                deuErro = true
                erro = e.message ?: "Erro desconhecido"
            }
        }
    }

    fun getKpisFinancas(mes: Int, ano: Int) {
        getTotalReceitasByMes(mes, ano)
        getTotalDespesasByMes(mes, ano)
        getTotalLucroByMes(mes, ano)
        getTotalComissoesByMes(mes, ano)
    }

    fun getTotalReceitasMes(mes: Int, ano: Int): Double {
        getTotalReceitasByMes(mes, ano)
        return totalReceitas
    }

    private fun getTotalReceitasByMes(mes: Int, ano: Int) {
        GlobalScope.launch {
            try {
                val response = financasService.getKpiReceitasMes(
                    empresaId = dataStoreRepository.getEmpresaId(),
                    mes = mes,
                    ano = ano
                )

                if (response.isSuccessful) {
                    deuErro = false
                    erro = ""
                    totalReceitas = response.body() ?: 0.0
                } else {
                    Log.e(
                        "api",
                        "Erro ao buscar total de receitas => ${response.errorBody()?.string()}"
                    )
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao buscar total de receitas => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun getTotalDespesasMes(mes: Int, ano: Int): Double {
        getTotalDespesasByMes(mes, ano)
        return totalDespesas
    }

    private fun getTotalDespesasByMes(mes: Int, ano: Int) {
        GlobalScope.launch {
            try {
                val response = financasService.getKpiDespesaMes(
                    empresaId = dataStoreRepository.getEmpresaId(),
                    mes = mes,
                    ano = ano
                )

                if (response.isSuccessful) {
                    deuErro = false
                    erro = ""
                    totalDespesas = response.body() ?: 0.0
                } else {
                    Log.e(
                        "api",
                        "Erro ao buscar total de despesas => ${response.errorBody()?.string()}"
                    )
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao buscar total de despesas => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun getTotalLucroMes(mes: Int, ano: Int): Double {
        getTotalLucroByMes(mes, ano)
        return totalLucro
    }

    private fun getTotalLucroByMes(mes: Int, ano: Int) {
        GlobalScope.launch {
            try {
                val response = financasService.getKpiLucroMes(
                    empresaId = dataStoreRepository.getEmpresaId(),
                    mes = mes,
                    ano = ano
                )

                if (response.isSuccessful) {
                    deuErro = false
                    erro = ""
                    totalLucro = response.body() ?: 0.0
                } else {
                    Log.e(
                        "api",
                        "Erro ao buscar total de lucro => ${response.errorBody()?.string()}"
                    )
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao buscar total de lucro => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun getTotalComissoesMes(mes: Int, ano: Int): Double {
        getTotalComissoesByMes(mes, ano)
        return totalComissoes
    }

    private fun getTotalComissoesByMes(mes: Int, ano: Int) {
        GlobalScope.launch {
            try {
                val response = financasService.getKpiComissaoMes(
                    empresaId = dataStoreRepository.getEmpresaId(),
                    mes = mes,
                    ano = ano
                )

                if (response.isSuccessful) {
                    deuErro = false
                    erro = ""
                    totalComissoes = response.body() ?: 0.0
                } else {
                    Log.e(
                        "api",
                        "Erro ao buscar total de comissões => ${response.errorBody()?.string()}"
                    )
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao buscar total de comissões => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }
}
package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import formatarDataDatePicker
import formatarDoubleBd
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.CategoriaDespesa
import school.sptech.data.model.Despesa
import school.sptech.data.service.CategoriaDespesaService
import school.sptech.data.service.DespesaService
import school.sptech.network.RetrofitService
import transformarEmLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime

class DespesaViewModel : ViewModel() {
    private val despesaService: DespesaService;
    private val categoriaDespesaService: CategoriaDespesaService;

    var listaDespesas = mutableStateListOf<Despesa>()
    var listaCategoriasDespesa by mutableStateOf(listOf<CategoriaDespesa>())

    var despesa by mutableStateOf(Despesa(empresaId = 1))
    var categoriaDespesa by mutableStateOf(CategoriaDespesa())

    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    init {
        despesaService = RetrofitService.getClientDespesa()
        categoriaDespesaService = RetrofitService.getClientCategoriaDespesa()
    }

    fun getCategoriasDespesa() {
        GlobalScope.launch {
            try {
                val response = categoriaDespesaService.getAllCategoriaDespesa()

                if (response.isSuccessful) {
                    listaCategoriasDespesa = response.body()!!
                    deuErro = false
                } else {
                    Log.e("api", "Erro ao buscar categorias de despesas => ${response.errorBody()?.string()}")
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao buscar categorias de produtos => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun getDespesas(empresaId: Int, dtSelecionada: LocalDate) {
        GlobalScope.launch {
            try {
                val response = despesaService.getAllDespesasByEmpresaIdAndMesAndAno(
                    empresaId = empresaId,
                    mes = dtSelecionada.monthValue,
                    ano = dtSelecionada.year
                )

                if (response.isSuccessful) {
                    listaDespesas.clear()
                    listaDespesas.addAll(response.body() ?: listOf())
                    deuErro = false
                } else {
                    Log.e("api", "Erro ao buscar despesas => ${response.errorBody()?.string()}")
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao buscar despesas => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun adicionarDespesa() {
        GlobalScope.launch {
            try {
                val dtPagamento = despesa.dtPagamento
                val categoriaDespesaId = listaCategoriasDespesa.find {
                    it.nome.equals(categoriaDespesa.nome)
                }?.id

                despesa.dtCriacao = LocalDateTime.now().toString()
                despesa.dtPagamento = transformarEmLocalDateTime(
                    formatarDataDatePicker(inputFormat = true, data = dtPagamento!!.toLong()))
                    .toString()

                despesa.valor =  formatarDoubleBd(despesa.valor ?: "0,00")
                val response = despesaService.postDespesa(
                    empresaId = despesa.empresaId ?: 0,
                    despesa = despesa,
                    categoriaDespesaId = categoriaDespesaId ?: 0
                )

                if(response.isSuccessful){
                    despesa.dtPagamento = dtPagamento
                    deuErro = false
                } else {
                    Log.e("api", "Erro ao adicionar despesa => ${response.errorBody()?.string()}")
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao adicionar despesa => ${ex.message}")
                deuErro = true
                erro = ("erro " + ex.message) ?: "Erro desconhecido"
            }
        }
    }
}
package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import formatarData
import formatarDataBd
import formatarDataDatePicker
import formatarDecimal
import formatarDoubleBd
import getLongDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.CategoriaDespesa
import school.sptech.data.model.Despesa
import school.sptech.data.model.FiltroDespesa
import school.sptech.data.service.CategoriaDespesaService
import school.sptech.data.service.DespesaService
import school.sptech.dataStoreRepository
import school.sptech.network.RetrofitService
import transformarEmLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime

class DespesaViewModel : ViewModel() {
    private val despesaService: DespesaService;
    private val categoriaDespesaService: CategoriaDespesaService;

    var listaDespesas = mutableStateListOf<Despesa>()
    var listaCategoriasDespesa by mutableStateOf(listOf<CategoriaDespesa>())

    var despesa by mutableStateOf(Despesa())
    var filtro by mutableStateOf(FiltroDespesa())

    //    private var novaDespesa by mutableStateOf(Despesa())
    var totalDespesas by mutableStateOf(0.0)

    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    var data by mutableStateOf(formatarDataBd(LocalDate.now()))

    init {
        despesaService = RetrofitService.getClientDespesa()
        categoriaDespesaService = RetrofitService.getClientCategoriaDespesa()
    }

    fun getCategoriasDespesa() {
        GlobalScope.launch {
            try {
                erro = ""
                val response = categoriaDespesaService.getAllCategoriaDespesa()

                if (response.isSuccessful) {
                    listaCategoriasDespesa = response.body() ?: listOf()
                    listaCategoriasDespesa = listaCategoriasDespesa.sortedBy { it.nome }
                    deuErro = false
                } else {
                    Log.e(
                        "api",
                        "Erro ao buscar categorias de despesas => ${response.errorBody()?.string()}"
                    )
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

    fun getListaDespesas(): List<Despesa> {
        return listaDespesas.toList()
    }

    fun getDespesas(empresaId: Int): List<Despesa> {
        getDespesasByEmpresaId(empresaId)
        return listaDespesas
    }

    private fun getDespesasByEmpresaId(empresaId: Int) {
        GlobalScope.launch {
            try {
                val response = despesaService.getAllDespesas(empresaId)

                if (response.isSuccessful) {
                    listaDespesas.clear()
                    listaDespesas.addAll(response.body() ?: listOf())
                    deuErro = false
                    erro = ""
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

    fun getDespesasPorData(empresaId: Int): List<Despesa> {
        getDespesasByData(empresaId)
        return listaDespesas
    }

    private fun getDespesasByData(empresaId: Int) {
        GlobalScope.launch {
            try {
                val response = despesaService.getAllDespesasByEmpresaIdAndData(
                    empresaId = empresaId,
                    data = data
                )

                if (response.isSuccessful) {
                    listaDespesas.clear()
                    listaDespesas.addAll(response.body() ?: listOf())
                    deuErro = false
                    erro = ""
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
                val novaDespesa = Despesa(
                    nome = despesa.nome,
                    observacao = despesa.observacao,
                    valor = formatarDoubleBd(despesa.valor ?: "0,00"),
                    formaPagamento = despesa.formaPagamento,
                    empresaId = dataStoreRepository.getEmpresaId(),
                    categoriaDespesaId = listaCategoriasDespesa.find {
                        it.nome.equals(despesa.categoriaDespesaNome)
                    }?.id,
                    dtCriacao = LocalDateTime.now().toString(),
                    dtPagamento = transformarEmLocalDateTime(
                        formatarDataDatePicker(
                            inputFormat = true,
                            data = despesa.dtPagamento!!.toLong()
                        )
                    )
                        .toString()
                )

                val response = despesaService.postDespesa(
                    empresaId = novaDespesa.empresaId ?: 0,
                    categoriaDespesaId = novaDespesa.categoriaDespesaId ?: 0,
                    despesa = novaDespesa
                )

                if (response.isSuccessful) {
                    deuErro = false
                    erro = "${response.message()} - OK"
                } else {
                    Log.e("api", "Erro ao adicionar despesa => ${response.errorBody()?.string()}")
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }

            } catch (ex: Exception) {
                Log.e("api", "Erro ao adicionar despesa => ${ex.message}")
                deuErro = true
                erro = (ex.message) ?: "Erro desconhecido"
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
                val response = despesaService.getTotalDespesasByEmpresaIdAndMesAndAno(
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

    fun filtrarDespesas(): List<Despesa> {
        val dtPagamentoFiltroFormatada = if (filtro.dtPagamento.isNotEmpty()) {
            formatarDataDatePicker(
                inputFormat = true,
                data = filtro.dtPagamento.toLong()
            )
        } else ""

        val despesasFiltradas = listaDespesas.filter {
            (filtro.categorias.isEmpty() || filtro.categorias.contains(it.categoriaDespesaNome))
                    && (filtro.dtPagamento.isEmpty() || dtPagamentoFiltroFormatada == formatarData(
                it.dtPagamento!!
            ))
                    && (filtro.formasPagamento.isEmpty() || filtro.formasPagamento.contains(it.formaPagamento))
                    && (it.valor?.toDoubleOrNull() ?: 0.0 >= filtro.valorMinimo.div(100))
                    && (filtro.valorMaximo == 0.0 || it.valor?.toDoubleOrNull() ?: 0.0 <= filtro.valorMaximo.div(
                100
            ))
        }

        return despesasFiltradas
    }

    fun limparFiltro() {
        filtro = FiltroDespesa()
    }

    fun filtroIsEmpty(): Boolean {
        return filtro.categorias.isEmpty() && filtro.dtPagamento.isEmpty() && filtro.formasPagamento.isEmpty() && filtro.valorMinimo == 0.0 && filtro.valorMaximo == 0.0
    }

    fun getDespesaById(despesaId: Int) {
        GlobalScope.launch {
            try {
                val response = despesaService.getDespesaById(
                    empresaId = dataStoreRepository.getEmpresaId(),
                    despesaId = despesaId
                )

                if (response.isSuccessful) {
                    despesa = response.body()!!
                    val valorFormatado = despesa.valor?.let { formatarDecimal(it.toDouble()) }
                    despesa = despesa.copy(valor = valorFormatado)
                    deuErro = false
                    erro = ""
                } else {
                    Log.e("api", "Erro ao buscar despesa => ${response.errorBody()?.string()}")
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao buscar despesa => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun salvarDespesa() {
        GlobalScope.launch {
            try {
                val valor = formatarDoubleBd(despesa.valor ?: "0,00")
                val novaDespesa = despesa.copy(
                    valor = valor,
                    categoriaDespesaId = listaCategoriasDespesa.find {
                        it.nome.equals(despesa.categoriaDespesaNome)
                    }?.id,
                    dtPagamento = transformarEmLocalDateTime(
                        formatarDataDatePicker(
                            data = despesa.dtPagamento!!.toLongOrNull()
                                ?: getLongDate(despesa.dtPagamento!!),
                            inputFormat = true
                        )
                    ).toString()
                )

                val response = despesaService.putDespesaById(
                    empresaId = novaDespesa.empresaId!!,
                    despesaId = novaDespesa.id ?: 0,
                    categoriaDespesaId = novaDespesa.categoriaDespesaId ?: 0,
                    despesa = novaDespesa
                )

                if (response.isSuccessful) {
                    deuErro = false
                    erro = "Despesa atualizada com sucesso!"
                } else {
                    Log.e("api", "Erro ao salvar despesa => ${response.errorBody()?.string()}")
                    deuErro = true
                    erro = response.message()
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao salvar despesa => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun deletarDespesa() {
        GlobalScope.launch {
            try {
                val response = despesaService.deleteDespesa(
                    empresaId = dataStoreRepository.getEmpresaId(),
                    despesaId = despesa.id ?: 0
                )

                if (response.isSuccessful) {
                    deuErro = false
                    erro = "Despesa excluída com sucesso!"
                } else {
                    Log.e("api", "Erro ao deletar despesa => ${response.errorBody()?.string()}")
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao deletar despesa => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }
}
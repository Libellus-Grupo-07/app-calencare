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
import school.sptech.preferencesHelper
import transformarEmLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime

class DespesaViewModel : ViewModel() {
    private val despesaService: DespesaService;
    private val categoriaDespesaService: CategoriaDespesaService;

    var listaDespesas = mutableStateListOf<Despesa>()
    var listaCategoriasDespesa by mutableStateOf(listOf<CategoriaDespesa>())

    var despesa by mutableStateOf(Despesa())

    //    private var novaDespesa by mutableStateOf(Despesa())
    var categoriaDespesa by mutableStateOf(CategoriaDespesa())

    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    private var mesAtual by mutableStateOf(0)
    private var anoAtual by mutableStateOf(0)

    var mes by mutableStateOf(LocalDate.now().monthValue)
    var ano by mutableStateOf(LocalDate.now().year)

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
                    listaCategoriasDespesa = response.body()!!
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

    fun atualizarDespesas(mes: Int, ano: Int) {
        this.mes = mes
        this.ano = ano

        getDespesas()
    }

    fun getDespesas() {
        GlobalScope.launch {
            try {
                val response = despesaService.getAllDespesasByEmpresaIdAndMesAndAno(
                    empresaId = preferencesHelper.getIdEmpresa(),
                    mes = mes,
                    ano = ano
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
                val novaDespesa = Despesa(
                    nome = despesa.nome,
                    observacao = despesa.observacao,
                    valor = formatarDoubleBd(despesa.valor ?: "0,00"),
                    formaPagamento = despesa.formaPagamento,
                    empresaId = despesa.empresaId,
                    categoriaDespesaId = listaCategoriasDespesa.find {
                        it.nome.equals(categoriaDespesa.nome)
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
                    erro = "Despesa adicionada com sucesso!"
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
}
package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.service.MovimentacaoValidadeService
import school.sptech.network.RetrofitService
import school.sptech.preferencesHelper

class MovimentacaoValidadeViewModel : ViewModel() {
    private val movimentacaoValidadeService:MovimentacaoValidadeService
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")
    private val empresaId = preferencesHelper.getIdEmpresa()
    var qtdProdutosSemEstoque by mutableStateOf(0)
    var qtdProdutosEstoqueBaixo by mutableStateOf(0)
    var qtdProdutosEstoqueAlto by mutableStateOf(0)
    var qtdProdutosRepostosNoDia by mutableStateOf(0)

    init {
        movimentacaoValidadeService = RetrofitService.getClientMovimentacaoValidade()
    }

    fun getKpisEstoque(){
        getProdutosSemEstoque()
        getProdutosEstoqueBaixo()
        getProdutosEstoqueAlto()
        getProdutosRepostosNoDia()
    }

    fun getProdutosSemEstoque() {
        GlobalScope.launch {
            try {
                val response = movimentacaoValidadeService.getProdutosSemEstoque(empresaId)
                if (response.isSuccessful) {
                    qtdProdutosSemEstoque = response.body() ?: 0
                    deuErro = false
                    erro = "OK"
                } else {
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (e: Exception) {
                Log.e("api", "Erro ao buscar produtos sem estoque => ${e.message}")
                deuErro = true
                erro = e.message ?: "Erro desconhecido"
            }
        }
    }

    fun getProdutosEstoqueBaixo() {
        GlobalScope.launch {
            try {
                val response = movimentacaoValidadeService.getProdutosEstoqueAbaixo(empresaId)
                if (response.isSuccessful) {
                    qtdProdutosEstoqueBaixo = response.body() ?: 0
                    deuErro = false
                    erro = "OK"
                } else {
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (e: Exception) {
                Log.e("api", "Erro ao buscar produtos com estoque abaixo => ${e.message}")
                deuErro = true
                erro = e.message ?: "Erro desconhecido"
            }
        }
    }

    fun getProdutosEstoqueAlto() {
        GlobalScope.launch {
            try {
                val response = movimentacaoValidadeService.getProdutosEstoqueAlto(empresaId)
                if (response.isSuccessful) {
                    qtdProdutosEstoqueAlto = response.body() ?: 0
                    deuErro = false
                    erro = "OK"
                } else {
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (e: Exception) {
                Log.e("api", "Erro ao buscar produtos com estoque alto => ${e.message}")
                deuErro = true
                erro = e.message ?: "Erro desconhecido"
            }
        }
    }

    fun getProdutosRepostosNoDia() {
        GlobalScope.launch {
            try {
                val response = movimentacaoValidadeService.getProdutosRepostosNoDia(empresaId)
                if (response.isSuccessful) {
                    qtdProdutosRepostosNoDia = response.body() ?: 0
                    deuErro = false
                    erro = "OK"
                } else {
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (e: Exception) {
                Log.e("api", "Erro ao buscar produtos repostos no dia => ${e.message}")
                deuErro = true
                erro = e.message ?: "Erro desconhecido"
            }
        }
    }

}
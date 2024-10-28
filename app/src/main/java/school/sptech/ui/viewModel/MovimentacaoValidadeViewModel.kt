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
    private var qtdProdutosSemEstoque by mutableStateOf(0)
    private var qtdProdutosEstoqueBaixo by mutableStateOf(0)
    private var qtdProdutosEstoqueAlto by mutableStateOf(0)
    private var qtdProdutosRepostosNoDia by mutableStateOf(0)

    init {
        movimentacaoValidadeService = RetrofitService.getClientMovimentacaoValidade()
    }

    fun getKpisEstoque(empresaId: Int){
        getProdutosSemEstoque(empresaId)
        getProdutosEstoqueBaixo(empresaId)
        getProdutosEstoqueAlto(empresaId)
        getProdutosRepostosNoDia(empresaId)
    }

    fun getQuantidadeProdutosSemEstoque(empresaId: Int): Int {
        getProdutosSemEstoque(empresaId)
        return qtdProdutosSemEstoque
    }

    fun getQuantidadeProdutosEstoqueBaixo(empresaId: Int): Int {
        getProdutosEstoqueBaixo(empresaId)
        return qtdProdutosEstoqueBaixo
    }

    fun getQuantidadeProdutosEstoqueAlto(empresaId: Int): Int {
        getProdutosEstoqueAlto(empresaId)
        return qtdProdutosEstoqueAlto
    }

    fun getQuantidadeProdutosRepostosNoDia(empresaId: Int): Int {
        getProdutosRepostosNoDia(empresaId)
        return qtdProdutosRepostosNoDia
    }

    private fun getProdutosSemEstoque(empresaId:Int) {
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

    fun getProdutosEstoqueBaixo(empresaId: Int) {
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

    fun getProdutosEstoqueAlto(empresaId: Int) {
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

    fun getProdutosRepostosNoDia(empresaId: Int) {
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
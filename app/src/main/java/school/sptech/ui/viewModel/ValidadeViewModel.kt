package school.sptech.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.MovimentacaoValidade
import school.sptech.data.model.Validade
import school.sptech.data.service.ValidadeService
import school.sptech.data.service.MovimentacaoValidadeService
import school.sptech.network.RetrofitService
import java.time.LocalDateTime

class ValidadeViewModel : ViewModel() {
    private val validadeService: ValidadeService;
    private val movimentacaoValidadeService: MovimentacaoValidadeService;

    var listaValidades = mutableStateListOf<Validade>()
    private var produtoId by mutableStateOf(0)
    var validade by mutableStateOf(Validade(
        dtCriacao = LocalDateTime.now().toString()
    ))
    var movimentacaoValidade by mutableStateOf(MovimentacaoValidade())
    var quantidadeEstoqueValidade by mutableStateOf(0)
    var quantidadeTotalEstoque by mutableStateOf(0)
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    init {
        validadeService = RetrofitService.getClientValidade()
        movimentacaoValidadeService = RetrofitService.getClientMovimentacaoValidade()
    }

    fun adicionarValidade(){
        GlobalScope.launch {
            try {
                val response = validadeService.adicionarValidade(validade)

                if(response.isSuccessful){
                    deuErro = false
                    erro = "Validade cadastrada com sucesso"
                    validade = Validade()
                } else {
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception){
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun getValidades(produtoId: Int): List<Validade> {
        this.produtoId = produtoId
        getValidades()
        return listaValidades
    }

    private fun getValidades() {
        GlobalScope.launch {
            try {
                val response = validadeService.getValidades(produtoId)

                if (response.isSuccessful) {
                    listaValidades.clear()
                    listaValidades.addAll(response.body() ?: listOf())
                    deuErro = false
                } else {
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun getTotalEstoqueProduto(produtoId: Int): Int {
        this.produtoId = produtoId
        getTotalEstoqueProduto()
        return quantidadeTotalEstoque
    }

    private fun getTotalEstoqueProduto() {
        GlobalScope.launch {
            try {
                val response = movimentacaoValidadeService.getTotalEstoque(produtoId)

                if (response.isSuccessful) {
                    quantidadeTotalEstoque = response.body()!!
                    deuErro = false
                } else {
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun getQuantidadeEstoqueValidade(validadeId: Int): Int {
        validade.id = validadeId
        getQuantidadeEstoquePorValidade()
        return quantidadeEstoqueValidade
    }

    private fun getQuantidadeEstoquePorValidade(){
        GlobalScope.launch {
            try {
                val response = movimentacaoValidadeService.getQuantidadePorValidade(validadeId = validade.id!!)

                if(response.isSuccessful){
                    quantidadeEstoqueValidade = response.body()!!
                    deuErro = false
                } else {
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception){
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun retirarEstoque(){
        adicionarMovimentacao(0)
    }

    fun reporEstoque(){
        adicionarMovimentacao(1)
    }

    private fun adicionarMovimentacao(tipoMovimentacao:Int){
        GlobalScope.launch {
            try {
                movimentacaoValidade.tipoMovimentacao = tipoMovimentacao.toString()
                movimentacaoValidade.idValidade = validade.id

                val response = movimentacaoValidadeService.postMovimentacaoValidade(movimentacaoValidade)
                if(response.isSuccessful){
                    deuErro = false
                    erro = "Movimentação realizada com sucesso"
                    movimentacaoValidade = MovimentacaoValidade()
                } else {
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception){
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

}
package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.NotificacaoEstoque
import school.sptech.data.service.NotificacaoEstoqueService
import school.sptech.dataStoreRepository
import school.sptech.network.RetrofitService

class NotificacaoEstoqueViewModel : ViewModel() {
    private val notificacaoEstoqueService: NotificacaoEstoqueService
    var notificacoes = mutableStateListOf<NotificacaoEstoque>()
    var notificacao = mutableStateOf(NotificacaoEstoque())
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    init {
        notificacaoEstoqueService = RetrofitService.getClientNotificacaoEstoque()
    }

    fun getNotificacoesEstoque() {
        GlobalScope.launch {
            try {
                val response =
                    notificacaoEstoqueService.getNotificacoesEstoque(dataStoreRepository.getEmpresaId())
                if (response.isSuccessful) {
                    notificacoes.clear()
                    notificacoes.addAll(response.body() ?: emptyList())
                    deuErro = false
                    erro = ""
                } else {
                    Log.e("api", response.errorBody().toString())
                    deuErro = true
                    erro = response.errorBody().toString()
                }
            } catch (e: Exception) {
                Log.e("api", e.message.toString())
                deuErro = true
                erro = e.message.toString()
            }
        }
    }

    fun marcarComoLida(notificacaoId: Int) {
        GlobalScope.launch {
            try {
                val response = notificacaoEstoqueService.marcarComoLida(notificacaoId)
                if (response.isSuccessful) {
                    getNotificacoesEstoque()
                    deuErro = false
                    erro = ""
                } else {
                    Log.e("api", response.errorBody().toString())
                    deuErro = true
                    erro = response.errorBody().toString()
                }
            } catch (e: Exception) {
                Log.e("api", e.message.toString())
                deuErro = true
                erro = e.message.toString()
            }
        }
    }

    fun marcarTodasComoLida() {
        GlobalScope.launch {
            try {
                val response =
                    notificacaoEstoqueService.marcarTodasComoLida(dataStoreRepository.getEmpresaId())

                if (response.isSuccessful) {
                    getNotificacoesEstoque()
                    deuErro = false
                    erro = ""
                } else {
                    Log.e("api", response.errorBody().toString())
                    deuErro = true
                    erro = response.errorBody().toString()
                }
            } catch (e: Exception) {
                Log.e("api", e.message.toString())
                deuErro = true
                erro = e.message.toString()
            }
        }
    }

    fun existNotificacaoNaoLida(): Boolean {
        getNotificacoesEstoque()
        return notificacoes.any { it.lido == 0 }
    }
}
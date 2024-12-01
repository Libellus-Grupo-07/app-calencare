package school.sptech.ui.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class ReporProdutoViewModel : ViewModel() {
    private val _quantidade = mutableStateOf(0)
    var quantidadeMaxima by mutableStateOf<Int?>(null)
    var quantidadeEstoqueData = mutableStateOf(0)
    val quantidade: State<Int> = _quantidade

    fun aumentarQuantidade() {
        if (quantidadeMaxima == null) {
            _quantidade.value++
        } else if (_quantidade.value < quantidadeMaxima!!) {
            _quantidade.value++
        }
    }

    fun diminuirQuantidade() {
        if (_quantidade.value > 0) {
            _quantidade.value--
        }
    }

    fun setQuantidadeInicial(valor: Int) {
        _quantidade.value = valor
    }

    fun setValorQuantidadeMaxima(valor: Int?) {
        quantidadeMaxima = valor
    }
}
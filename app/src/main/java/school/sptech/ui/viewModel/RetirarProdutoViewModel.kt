package school.sptech.ui.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReporProdutoViewModel : ViewModel() {

    private val _quantidade = mutableStateOf(0)
    val quantidade: State<Int> = _quantidade

    fun aumentarQuantidade() {
        _quantidade.value++
    }

    fun diminuirQuantidade() {
        if (_quantidade.value > 0) {
            _quantidade.value--
        }
    }

    fun setQuantidadeInicial(valor: Int) {
        _quantidade.value = valor
    }
}

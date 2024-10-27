package school.sptech.ui.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ReporProdutoViewModel : ViewModel() {
    private val _quantidade = mutableStateOf(0)
    private var quantidadeMaxima = mutableStateOf(0)
    var quantidadeEstoqueData = mutableStateOf(0)
    val quantidade: State<Int> = _quantidade

    fun aumentarQuantidade() {
        if(quantidadeMaxima.value == 0){
            _quantidade.value++
        }
        else if(_quantidade.value < quantidadeMaxima.value){
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

    fun setQuantidadeMaxima(valor: Int) {
        quantidadeMaxima.value = valor
    }

    fun getQuantidadeMaxima(): Int {
        return quantidadeMaxima.value
    }
}

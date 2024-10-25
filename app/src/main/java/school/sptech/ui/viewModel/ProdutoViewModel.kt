package school.sptech.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.Produto
import school.sptech.data.service.ProdutoService
import school.sptech.network.RetrofitService

class ProdutoViewModel : ViewModel() {
    private val produtoService: ProdutoService;
    var produto by mutableStateOf(Produto())

    init {
        produtoService = RetrofitService.getClientProduto()
    }

    fun adicionarProduto(novoProduto: Produto) {
        GlobalScope.launch {
            try{

            } catch (ex: Exception) {

            }
        }
    }
}
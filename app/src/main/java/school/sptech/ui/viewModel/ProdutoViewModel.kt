package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import school.sptech.data.model.CategoriaProduto
import school.sptech.data.model.Produto
import school.sptech.data.model.Validade
import school.sptech.data.service.CategoriaProdutoService
import school.sptech.data.service.ProdutoService
import school.sptech.data.service.ValidadeService
import school.sptech.network.RetrofitService

class ProdutoViewModel : ViewModel() {
    private val produtoService: ProdutoService;
    private val categoriaProdutoService: CategoriaProdutoService;
    private val validadeService: ValidadeService;

    var produtos by mutableStateOf(listOf<Produto>())
    var categoriasProduto by mutableStateOf(listOf<CategoriaProduto>())
    var produto by mutableStateOf(Produto(empresaId = 1))
    var categoriaProduto by mutableStateOf(CategoriaProduto())
    var validade by mutableStateOf(Validade())
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    init {
        produtoService = RetrofitService.getClientProduto()
        categoriaProdutoService = RetrofitService.getClientCategoriaProduto()
        validadeService = RetrofitService.getClientValidade()
    }

    fun getCategoriasProduto() {
        GlobalScope.launch {
            try {
                val response = categoriaProdutoService.getAllCategoriaProduto()

                if (response.isSuccessful) {
                    categoriasProduto = response.body()!!
                    deuErro = false
                } else {
                    Log.e("api", "Erro ao buscar categorias de produtos => ${response.errorBody()?.string()}")
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

    fun getProdutos(empresaId: Int) {
        GlobalScope.launch {
            try {
                val response = produtoService.getAllProdutosByEmpresaId(empresaId)

                if (response.isSuccessful) {
                    produtos = response.body()!!
                } else {
                    Log.e("api", "Erro ao buscar produtos => ${response.errorBody()?.string()}")
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao buscar produtos => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun adicionarProduto() {
        GlobalScope.launch {
            try {
                produto.catagoriaProdutoId = categoriasProduto.find({ it.nome == categoriaProduto.nome })?.id
                val response = produtoService.adicionarProduto(produto)

                if(response.isSuccessful){
                    deuErro = false
                } else {
                    Log.e("api", "Erro ao adicionar produto => ${response.errorBody()?.string()}")
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao adicionar produto => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }
}
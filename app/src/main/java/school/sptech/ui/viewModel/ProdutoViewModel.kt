package school.sptech.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import school.sptech.network.RetrofitService
import school.sptech.preferencesHelper

class ProdutoViewModel : ViewModel() {
    //class ProdutoViewModel(private val validadeViewModel: ValidadeViewModel) : ViewModel() {
    private val produtoService: ProdutoService;
    private val categoriaProdutoService: CategoriaProdutoService;

    private var empresaId by mutableStateOf(0)
    var produtos = mutableStateListOf<Produto>()
    var categoriasProduto by mutableStateOf(listOf<CategoriaProduto>())
    var produto by mutableStateOf(Produto(empresaId = preferencesHelper.getIdEmpresa()))
    private var _produtoAtual by mutableStateOf(Produto(empresaId = preferencesHelper.getIdEmpresa()))
    var categoriaProduto by mutableStateOf(CategoriaProduto())
    var validade by mutableStateOf(Validade())
    var deuErro by mutableStateOf(false)
    var erro by mutableStateOf("")

    init {
        produtoService = RetrofitService.getClientProduto()
        categoriaProdutoService = RetrofitService.getClientCategoriaProduto()
    }

    fun getListaProdutos(): List<Produto> {
        return produtos.toList()
    }

    fun getCategoriasProduto() {
        GlobalScope.launch {
            try {
                val response = categoriaProdutoService.getAllCategoriaProduto()

                if (response.isSuccessful) {
                    categoriasProduto = response.body()!!
                    deuErro = false
                } else {
                    Log.e(
                        "api",
                        "Erro ao buscar categorias de produtos => ${response.errorBody()?.string()}"
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

    fun getProdutoAtual(): Produto {
        _produtoAtual = produto
        return _produtoAtual
    }

    fun getProduto(empresaId: Int, produtoId: Int): Produto {
        getProdutoById(empresaId, produtoId)
        return produto
    }

    fun getProdutoById(empresaId: Int, produtoId: Int) {
        GlobalScope.launch {
            try {
                val response = produtoService.getProdutoById(empresaId, produtoId)

                if (response.isSuccessful) {
                    produto = response.body()!!
                    deuErro = false
                } else {
                    Log.e("api", "Erro ao buscar produto => ${response.errorBody()?.string()}")
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }
            } catch (ex: Exception) {
                Log.e("api", "Erro ao buscar produto => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }

    fun getProdutos(empresaId: Int): List<Produto> {
        this.empresaId = empresaId
        getProdutos()
//        getValidades()
//        getQuantidadeTotalEstoque()
        return produtos.toList()
    }

    private fun getProdutos() {
        GlobalScope.launch {
            try {
                val response = produtoService.getAllProdutosByEmpresaId(preferencesHelper.getIdEmpresa())
                erro = response.errorBody()?.string() ?: "Erro desconhecido"

                if (response.isSuccessful) {
                    produtos.clear()
                    produtos.addAll(response.body() ?: listOf())
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

    private fun getValidades() {
        GlobalScope.launch {
            produtos.forEach { produto ->
//               produto.validades = validadeViewModel.getValidades(produto.id!!)
            }
        }
    }

    private fun getQuantidadeTotalEstoque() {
        GlobalScope.launch {
            produtos.forEach { produto ->
//                produto.qtdEstoque = validadeViewModel.getTotalEstoqueProduto(produto.id!!)
            }
        }
    }

    fun adicionarProduto() {
        GlobalScope.launch {
            try {
                produto.categoriaProdutoId =
                    categoriasProduto.find({ it.nome == categoriaProduto.nome })?.id
                val response = produtoService.adicionarProduto(produto)

                if (response.isSuccessful) {
                    deuErro = false
                    erro = "OK"
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

    fun atualizarProduto() {
        GlobalScope.launch {
            try {
                val categoriaProdutoId =
                    categoriasProduto.find{ it.nome == produto.categoriaProdutoNome }?.id

                val produtoAtualizado = Produto(
                    nome = produto.nome,
                    marca = produto.marca,
                    descricao = produto.descricao,
                    categoriaProdutoId = categoriaProdutoId,
                )

                val response = produtoService.atualizarProduto(
                    produtoId = produto.id ?: 0,
                    empresaId = produto.empresaId ?: 0,
                    produto = produtoAtualizado
                )

                if (response.isSuccessful) {
                    deuErro = false
                    erro = "Produto atualizado com sucesso!"
                } else {
                    Log.e("api", "Erro ao atualizar produto => ${response.errorBody()?.string()}")
                    deuErro = true
                    erro = response.errorBody()?.string() ?: "Erro desconhecido"
                }

            } catch (ex: Exception) {
                Log.e("api", "Erro ao atualizar produto => ${ex.message}")
                deuErro = true
                erro = ex.message ?: "Erro desconhecido"
            }
        }
    }
}


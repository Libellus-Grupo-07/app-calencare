package school.sptech

sealed class Routes(val route:String) {
    object Splash : Routes("splash")
    object Login : Routes("login")
    object Notificacoes : Routes("notificacoes")
    object AdicionarProduto : Routes("adicionar_produto")
    object AdicionarDespesa : Routes("adicionar_despesa")
    object DadosEmpresa: Routes("dados_empresa")
    object DadosPessoais: Routes("dados_pessoais")
//    object InformacoesProduto: Routes("informacoes_produto/{produtoId}")
    object InformacoesProduto: Routes("informacoes_produto")
    object InformacoesMovimentacao: Routes("informacoes_movimentos")
    object InformacoesDespesa: Routes("informacoes_despesa")
}
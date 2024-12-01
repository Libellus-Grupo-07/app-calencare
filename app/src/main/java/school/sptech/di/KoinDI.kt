package school.sptech.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import school.sptech.data.model.Funcionario
import school.sptech.ui.viewModel.DashFinancasViewModel
import school.sptech.ui.viewModel.FinancasViewModel
import school.sptech.ui.viewModel.MovimentacaoValidadeViewModel
import school.sptech.ui.viewModel.ProdutoViewModel
import school.sptech.ui.viewModel.UsuarioViewModel
import school.sptech.ui.viewModel.ValidadeViewModel

val modulesApp = module {
    single<UserSession>{
        UserSession()
    }

    single<Funcionario>{
        Funcionario()
    }

    viewModel { UsuarioViewModel() }
    viewModel { ProdutoViewModel() }
    viewModel { FinancasViewModel() }
    viewModel { DashFinancasViewModel() }
    viewModel { ValidadeViewModel() }
    viewModel { MovimentacaoValidadeViewModel() }
}
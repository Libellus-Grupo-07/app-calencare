package school.sptech.di

import org.koin.dsl.module

val modulesApp = module {
    single<UserSession>{
        UserSession()
    }
}
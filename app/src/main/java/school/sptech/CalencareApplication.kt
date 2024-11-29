package school.sptech

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import school.sptech.di.modulesApp

class CalencareApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@CalencareApplication)
            modules(modulesApp)
        }
    }
}
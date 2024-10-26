package school.sptech

import android.app.Application
import school.sptech.data.AppContainer
import school.sptech.data.AppDataContainer

class CalencareApp : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppDataContainer(this)
    }
}
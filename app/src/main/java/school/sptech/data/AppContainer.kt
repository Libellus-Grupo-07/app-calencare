package school.sptech.data

import android.content.Context
import school.sptech.data.database.UsuarioDatabase
import school.sptech.data.repository.OfflineUsuarioRepository
import school.sptech.data.repository.UsuarioRepository

interface AppContainer {
    val usuarioRepository: UsuarioRepository
}

class AppDataContainer(private val context: Context) : AppContainer{
    override val usuarioRepository: UsuarioRepository by lazy {
        OfflineUsuarioRepository(UsuarioDatabase.getDatabase(context).usuarioDao())
    }
}
package school.sptech.data.repository

import kotlinx.coroutines.flow.Flow
import school.sptech.data.dao.UsuarioDao
import school.sptech.data.entity.Usuario

class OfflineUsuarioRepository(private val usuarioDao: UsuarioDao) : UsuarioRepository {
    override suspend fun insertUsuario(usuario: Usuario) = usuarioDao.insert(usuario)

    override suspend fun deleteUsuario(usuario: Usuario) = usuarioDao.delete(usuario)

    override suspend fun getUsers(): Flow<List<Usuario>> = usuarioDao.getUsers()

    override suspend fun getId(): Flow<Int>  = usuarioDao.getId()

}
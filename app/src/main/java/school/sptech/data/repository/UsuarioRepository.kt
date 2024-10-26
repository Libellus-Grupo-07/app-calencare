package school.sptech.data.repository

import kotlinx.coroutines.flow.Flow
import school.sptech.data.entity.Usuario

interface UsuarioRepository{
    suspend fun insertUsuario(usuario: Usuario)
    suspend fun deleteUsuario(usuario: Usuario)
    suspend fun getUsers(): Flow<List<Usuario>>
    suspend fun getId(): Flow<Int>
}
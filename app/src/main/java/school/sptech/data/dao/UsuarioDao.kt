package school.sptech.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import school.sptech.data.entity.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insert(usuario: Usuario)
    @Delete
    suspend fun delete(usuario: Usuario)
    @Query("SELECT * FROM usuario")
    suspend fun getUsers(): Flow<List<Usuario>>
    @Query("SELECT id FROM usuario LIMIT 1")
    suspend fun getId(): Flow<Int>
}
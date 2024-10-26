package school.sptech.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import school.sptech.data.dao.UsuarioDao
import school.sptech.data.entity.Usuario


@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class UsuarioDatabase : RoomDatabase(){
    abstract fun usuarioDao(): UsuarioDao

    companion object{
        @Volatile
        private var Instance: UsuarioDatabase? = null

        fun getDatabase(context:Context) : UsuarioDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    UsuarioDatabase::class.java,
                    "usuario_database"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
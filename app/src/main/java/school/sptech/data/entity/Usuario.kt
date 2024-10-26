package school.sptech.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey val id:Int,
    val nome:String,
    val razaoSocialEmpresa:String,
    val empresaId:Int,
)
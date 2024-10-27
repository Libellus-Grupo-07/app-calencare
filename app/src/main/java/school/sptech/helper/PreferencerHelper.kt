package school.sptech.helper

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveIdEmpresa(idEmpresa: Int) {
        sharedPreferences.edit().putInt("idEmpresa", idEmpresa).apply()
    }

    fun saveIdUsuario(idUsuario: Int) {
        sharedPreferences.edit().putInt("idUsuario", idUsuario).apply()
    }

    fun getIdEmpresa(): Int {
        return sharedPreferences.getInt("idEmpresa", -1)
    }

    fun getIdUsuario(): Int {
        return sharedPreferences.getInt("idUsuario", -1)
    }
}
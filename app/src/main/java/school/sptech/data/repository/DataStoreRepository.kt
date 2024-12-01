package school.sptech.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import school.sptech.dataStore
import school.sptech.di.UserSession

class DataStoreRepository(context: Context) {
    private val dataStore = context.dataStore
    private val userId = intPreferencesKey("idUsuario")
    private val empresaId = intPreferencesKey("idEmpresa")
    private val userName = stringPreferencesKey("nome")
    private val empresaName = stringPreferencesKey("nomeEmpresa")
    private val userLogado = booleanPreferencesKey("logado")

    suspend fun saveUser(userSession: UserSession) {
        dataStore.edit { preferences ->
            preferences[userId] = userSession.idUser
            preferences[empresaId] = userSession.idEmpresa
            preferences[userName] = userSession.nome
            preferences[empresaName] = userSession.nomeEmpresa
            preferences[userLogado] = true
        }
    }

    suspend fun getUser(): UserSession {
        val preferences = dataStore.data.first()
        return UserSession(
            idUser = preferences[userId] ?: 0,
            idEmpresa = preferences[empresaId] ?: 0,
            nome = preferences[userName] ?: "",
            nomeEmpresa = preferences[empresaName] ?: ""
        )
    }

    suspend fun getUserId(): Int {
        val preferences = dataStore.data.first()
        return preferences[userId] ?: 0
    }

    suspend fun getEmpresaId(): Int {
        val preferences = dataStore.data.first()
        return preferences[empresaId] ?: 0
    }

    suspend fun isLogado(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[userLogado] ?: false
    }

    suspend fun alreadySaved(): Boolean {
        val user = getUser()
        return user.idUser != 0 && user.idEmpresa != 0 && user.nome != "" && user.nomeEmpresa != ""
    }

    fun logout(){
        GlobalScope.launch {
            clearUser()
        }
    }

    private suspend fun clearUser() {
        dataStore.edit { preferences ->
            preferences[userId] = 0
            preferences[empresaId] = 0
            preferences[userName] = ""
            preferences[empresaName] = ""
            preferences[userLogado] = false
        }
    }
}


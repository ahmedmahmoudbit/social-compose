import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "app_preferences")

@Suppress("UNCHECKED_CAST")
@Singleton
class CacheHelper@Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun setData(key: String, value: Any) {
        context.dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                is Double -> preferences[doublePreferencesKey(key)] = value
                else -> throw IllegalArgumentException("Unsupported data type")
            }
        }
    }

    fun <T> getData(key: String, defaultValue: T): Flow<T> {
        return context.dataStore.data.map { preferences ->
            when (defaultValue) {
                is String -> preferences[stringPreferencesKey(key)] ?: defaultValue
                is Int -> preferences[intPreferencesKey(key)] ?: defaultValue
                is Boolean -> preferences[booleanPreferencesKey(key)] ?: defaultValue
                is Double -> preferences[doublePreferencesKey(key)] ?: defaultValue
                else -> throw IllegalArgumentException("Unsupported data type")
            } as T
        }
    }

    suspend fun removeData(key: String) {
        context.dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
        }
    }

    suspend fun clearCache() {
        context.dataStore.edit { it.clear() }
    }
}

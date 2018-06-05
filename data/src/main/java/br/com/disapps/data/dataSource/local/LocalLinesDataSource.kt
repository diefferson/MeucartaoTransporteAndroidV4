package br.com.disapps.data.dataSource.local

import android.content.res.AssetManager
import br.com.disapps.data.BuildConfig
import br.com.disapps.data.api.DownloadTask
import br.com.disapps.data.dataSource.LinesDataSource
import br.com.disapps.data.entity.Linha
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.data.utils.deleteFromCache
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.listeners.DownloadProgressListener
import io.realm.Realm
import java.io.File
import java.io.FileInputStream


/**
 * Created by dnso on 15/03/2018.
 */
class LocalLinesDataSource(private val database: Database, private val preferences: Preferences,private val assetManager: AssetManager  ): LinesDataSource {

    companion object {
        private const val CODE = "codigo"
        private const val NAME = "nome"
        private val CLAZZ = Linha::class.java
    }

    override suspend fun saveLine(linha: Linha){
        val realm = database.getDatabase() as Realm
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(linha)
        realm.commitTransaction()
        realm.close()
    }

    override suspend fun saveAllLinesFromJson(filePath: String, downloadProgressListener: DownloadProgressListener) {
        val result = DownloadTask(downloadProgressListener).execute(BuildConfig.DOWNLOAD_LINES, "", filePath).get()
        if(result == "OK"){
            val realm = database.getDatabase() as Realm
            val fis = FileInputStream(File(filePath))
            realm.beginTransaction()
            realm.createOrUpdateAllFromJson(CLAZZ, fis)
            realm.commitTransaction()
            preferences.setLinesDate()
            realm.close()
            deleteFromCache(filePath)
        }else{
            throw KnownException(KnownError.UNKNOWN_EXCEPTION, result)
        }
    }

    override suspend fun saveAllLinesFromJson(filePath: String) {
            val realm = database.getDatabase() as Realm
            val fis = FileInputStream(File(filePath))
            realm.beginTransaction()
            realm.createOrUpdateAllFromJson(CLAZZ, fis)
            realm.commitTransaction()
            preferences.setLinesDate()
            realm.close()
            deleteFromCache(filePath)
    }

    override suspend fun lines(): List<Linha> {
        val realm = database.getDatabase() as Realm
        val lines = realm.copyFromRealm(realm.where(CLAZZ)
                                            .findAll()
                                            .sort(NAME))
        realm.close()
        return lines
    }

    override suspend fun line(linha: Linha): Linha {
        val realm = database.getDatabase() as Realm
        val line = realm.where(CLAZZ)
                        .equalTo(CODE, linha.codigo)
                        .findFirstAsync()
        realm.close()
        return line
    }

    override suspend fun updateLine(linha: Linha) {
        val realm = database.getDatabase() as Realm
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(linha)
        realm.commitTransaction()
        realm.close()
    }
}
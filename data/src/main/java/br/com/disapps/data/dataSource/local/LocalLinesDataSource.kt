package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.LinesDataSource
import br.com.disapps.data.entity.Linha
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.listeners.DownloadProgressListener
import io.realm.Realm

/**
 * Created by dnso on 15/03/2018.
 */
class LocalLinesDataSource(private val database: Database, private val preferences: Preferences): LinesDataSource {

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

    override suspend fun saveAllFromJson(json: String) {
        val realm = database.getDatabase() as Realm
        realm.beginTransaction()
        realm.createOrUpdateAllFromJson(CLAZZ, json)
        realm.commitTransaction()
        preferences.setLinesDate()
        realm.close()
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

    override suspend fun jsonLines( downloadProgressListener: DownloadProgressListener): String {
        throw Throwable("not implemented,  cloud only")
    }
}
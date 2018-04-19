package br.com.disapps.data.dataSource.local

import br.com.disapps.data.entity.Linha
import br.com.disapps.data.dataSource.LinesDataSource
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.listeners.DownloadProgressListener
import io.reactivex.Completable
import io.reactivex.Single
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

    override fun saveLine(linha: Linha): Completable{
        val realm = database.getDatabase() as Realm
        return Completable.defer {
            try {
                realm.beginTransaction()
                realm.copyToRealmOrUpdate(linha)
                realm.commitTransaction()
                Completable.complete()
            }catch (ec: Exception){
                Completable.error(ec)
            }finally {
                realm.close()
            }
        }
    }

    override fun saveAllFromJson(json: String): Completable {
        val realm = database.getDatabase() as Realm
        return Completable.defer {
            try {
                realm.beginTransaction()
                realm.createOrUpdateAllFromJson(CLAZZ, json)
                realm.commitTransaction()
                preferences.setLinesDate()
                Completable.complete()
            }catch (ec: Exception){
                Completable.error(ec)
            }finally {
                realm.close()
            }
        }
    }

    override fun lines(): Single<List<Linha>> {
        val realm = database.getDatabase() as Realm
        val lines = realm.copyFromRealm(realm.where(CLAZZ)
                                            .findAll()
                                            .sort(NAME))
        realm.close()
        return Single.just(lines)
    }

    override fun line(linha: Linha): Single<Linha> {
        val realm = database.getDatabase() as Realm
        val line = realm.where(CLAZZ)
                        .equalTo(CODE, linha.codigo)
                        .findFirst()
        realm.close()
        return Single.just(line)
    }

    override fun updateLine(linha: Linha): Completable {
        return Completable.defer {
            val realm = database.getDatabase() as Realm
            try {
                realm.beginTransaction()
                realm.copyToRealmOrUpdate(linha)
                realm.commitTransaction()
                Completable.complete()
            }catch (ec: Exception){
                Completable.error(ec)
            }finally {
                realm.close()
            }
        }
    }

    override fun jsonLines( downloadProgressListener: DownloadProgressListener): Single<String> {
        return Single.error<String>(Throwable("not implemented,  cloud only"))
    }
}
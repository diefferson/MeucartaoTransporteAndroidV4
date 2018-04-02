package br.com.disapps.data.repository.dataSource.lines

import br.com.disapps.data.entity.Linha
import br.com.disapps.data.storage.database.Database
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm

/**
 * Created by dnso on 15/03/2018.
 */
class LocalLinesDataSource(private var database: Database): LinesDataSource{

    override fun saveLine(linha: Linha): Completable{
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

    override fun saveAllFromJson(json: String): Completable {
        return Completable.defer {
            val realm = database.getDatabase() as Realm
            try {
                realm.beginTransaction()
                realm.createOrUpdateAllFromJson(Linha::class.java, json)
                realm.commitTransaction()
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
        val lines = realm.copyFromRealm(realm.where(Linha::class.java).findAll().sort("nome").toList())
        realm.close()
        return Single.just(lines)
    }

    override fun line(linha: Linha): Single<Linha> {
        val realm = database.getDatabase() as Realm
        val line = realm.where(Linha::class.java).equalTo("codigo", linha.codigo).findFirst()
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

    override fun jsonLines(): Single<String> {
        TODO("not implemented, cloud only")
    }
}
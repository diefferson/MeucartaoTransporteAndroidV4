package br.com.disapps.data.repository.dataSource.lines

import br.com.disapps.data.entity.Linha
import br.com.disapps.data.storage.database.Database
import io.reactivex.Observable
import io.realm.Realm

/**
 * Created by dnso on 15/03/2018.
 */
class LocalLinesDataSource(private var database: Database): LinesDataSource{

    override fun saveLine(linha: Linha):Observable<Boolean> {
        val realm = database.getDatabase() as Realm
        return try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(linha)
            realm.commitTransaction()
            Observable.just(true)
        }catch (ec: Exception){
            Observable.just(false)
        }finally {
            realm.close()
        }

    }

    override fun saveAllFromJson(json: String):Observable<Boolean> {
        val realm = database.getDatabase() as Realm
        return try {
            realm.beginTransaction()
            realm.createOrUpdateAllFromJson(Linha::class.java, json)
            realm.commitTransaction()
            Observable.just(true)
        }catch (ec: Exception){
            Observable.just(false)
        }finally {
            realm.close()
        }
    }

    override fun lines(): Observable<List<Linha>> {
        val realm = database.getDatabase() as Realm
        val lines = realm.copyFromRealm(realm.where(Linha::class.java).findAll().sort("nome").toList())
        realm.close()
        return Observable.just(lines)
    }

    override fun line(linha: Linha): Observable<Linha> {
        val realm = database.getDatabase() as Realm
        val line = realm.where(Linha::class.java).equalTo("codigo", linha.codigo).findFirst()
        realm.close()
        return Observable.just(line)
    }

    override fun updateLine(linha: Linha): Observable<Boolean> {
        val realm = database.getDatabase() as Realm
        return try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(linha)
            realm.commitTransaction()
            Observable.just(true)
        }catch (ec: Exception){
            Observable.just(false)
        }finally {
            realm.close()
        }
    }

    override fun jsonLines(): Observable<String> {
        TODO("not implemented, cloud only")
    }
}
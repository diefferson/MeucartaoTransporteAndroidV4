package br.com.disapps.data.repository.dataSource.lines

import br.com.disapps.data.entity.Linha
import br.com.disapps.data.storage.database.Database
import io.reactivex.Observable
import io.realm.Realm

/**
 * Created by dnso on 15/03/2018.
 */
class LocalLinesDataSource(private var database: Database): LinesDataSource{

    val realm : Realm by lazy { database.getDatabase() as Realm }

    override fun saveLine(linha: Linha) {
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(linha)
        realm.commitTransaction()
    }

    override fun saveAllFromJson(json: String) {
        realm.beginTransaction()
        realm.createOrUpdateAllFromJson(Linha::class.java, json)
        realm.commitTransaction()
    }

    override fun lines(): Observable<List<Linha>> {
        return Observable.just(realm.where(Linha::class.java).findAll().toList())
    }

    override fun line(linha: Linha): Observable<Linha> {
        return Observable.just(realm.where(Linha::class.java).equalTo("codigo", linha.codigo).findFirst())
    }

    override fun jsonLines(): Observable<String> {
        TODO("not implemented, cloud only")
    }
}
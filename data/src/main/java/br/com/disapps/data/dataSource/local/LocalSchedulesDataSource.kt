package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.SchedulesDataSource
import br.com.disapps.data.entity.HorarioLinha
import br.com.disapps.data.storage.database.Database
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm

class LocalSchedulesDataSource(private val database: Database) : SchedulesDataSource{

    override fun saveAllFromJson(json: String): Completable {
        return Completable.defer {
            val realm = database.getDatabase() as Realm
            try {
                realm.beginTransaction()
                realm.createOrUpdateAllFromJson(HorarioLinha::class.java, json)
                realm.commitTransaction()
                Completable.complete()
            }catch (ec: Exception){
                Completable.error(ec)
            }finally {
                realm.close()
            }
        }
    }

    override fun jsonSchedules(): Single<String> {
        return Single.error<String>(Throwable("not implemented,  cloud only"))
    }
}
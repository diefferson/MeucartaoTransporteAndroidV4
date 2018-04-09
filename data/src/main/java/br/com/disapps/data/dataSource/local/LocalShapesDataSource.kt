package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.ShapesDataSource
import br.com.disapps.data.entity.Shape
import br.com.disapps.data.storage.database.Database
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm

class LocalShapesDataSource(private val database: Database) : ShapesDataSource {

    override fun saveAllFromJson(json: String): Completable {
        return Completable.defer {
            val realm = database.getDatabase() as Realm
            try {
                realm.beginTransaction()
                realm.createOrUpdateAllFromJson(Shape::class.java, json)
                realm.commitTransaction()
                Completable.complete()
            }catch (ec: Exception){
                Completable.error(ec)
            }finally {
                realm.close()
            }
        }
    }

    override fun jsonShapes(city: String): Single<String> {
        return Single.error<String>(Throwable("not implemented,  cloud only"))
    }
}
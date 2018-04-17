package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.ShapesDataSource
import br.com.disapps.data.entity.Shape
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.model.City
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm

class LocalShapesDataSource(private val database: Database, private val preferences : Preferences) : ShapesDataSource {

    override fun saveAllFromJson(json: String, city: City): Completable {

        return Completable.defer {
            val realm = database.getDatabase() as Realm
            try {
                realm.beginTransaction()
                realm.createOrUpdateAllFromJson(Shape::class.java, json)
                realm.commitTransaction()

                if(city == City.CWB){
                    preferences.setCwbShapesDate()
                }else{
                    preferences.setMetShapesDate()
                }

                Completable.complete()
            }catch (ec: Exception){
                Completable.error(ec)
            }finally {
                realm.close()
            }
        }
    }

    override fun jsonShapes(city: City): Single<String> {
        return Single.error<String>(Throwable("not implemented,  cloud only"))
    }
}
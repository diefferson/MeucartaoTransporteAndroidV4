package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.ShapesDataSource
import br.com.disapps.data.entity.Ponto
import br.com.disapps.data.entity.Shape
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm

class LocalShapesDataSource(private val database: Database, private val preferences : Preferences) : ShapesDataSource {

    companion object {
        private const val CODE_LINE = "codigoLinha"
        private val CLAZZ = Shape::class.java
    }

    override fun saveAllFromJson(json: String, city: City): Completable {
        val realm = database.getDatabase() as Realm
        return Completable.defer {
            try {
                realm.beginTransaction()
                realm.createOrUpdateAllFromJson(CLAZZ, json)
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

    override fun getShapes(codeLine: String): Single<List<Shape>> {
        val realm = database.getDatabase() as Realm
        val shapes = realm.copyFromRealm(realm.where(CLAZZ)
                                            .equalTo(CODE_LINE, codeLine)
                                            .findAll())
        realm.close()
        return Single.just(shapes)
    }

    override fun jsonShapes(city: City, downloadProgressListener: DownloadProgressListener): Single<String> {
        return Single.error<String>(Throwable("not implemented,  cloud only"))
    }
}
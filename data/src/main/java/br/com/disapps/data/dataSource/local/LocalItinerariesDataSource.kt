package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.ItinerariesDataSource
import br.com.disapps.data.entity.Ponto
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm

class LocalItinerariesDataSource(private val database: Database, private val preferences:Preferences) : ItinerariesDataSource {

    override fun saveAllFromJson(json: String, city: City): Completable {

        return Completable.defer {
            val realm = database.getDatabase() as Realm
            try {
                realm.beginTransaction()
                realm.createOrUpdateAllFromJson(Ponto::class.java, json)
                realm.commitTransaction()

                if(city == City.CWB){
                    preferences.setCwbItinerariesDate()
                }else{
                    preferences.setMetItinerariesDate()
                }

                Completable.complete()
            }catch (ec: Exception){
                Completable.error(ec)
            }finally {
                realm.close()
            }
        }
    }

    override fun getItineraryDirections(codeLine: String): Single<List<String>> {
        val realm = database.getDatabase() as Realm
        val directions = realm.copyFromRealm(realm.where(Ponto::class.java).equalTo("codigoLinha", codeLine).distinct("sentido").findAll())
        return Single.just(directions.map { it.sentido })
    }

    override fun getItinerary(codeLine: String, direction: String): Single<List<Ponto>> {
        val realm = database.getDatabase() as Realm
        val itinerary = realm.copyFromRealm(realm.where(Ponto::class.java)
                            .equalTo("codigoLinha", codeLine)
                            .equalTo("sentido", direction)
                            .findAll().sort("sequencia"))
        return Single.just(itinerary)
    }

    override fun jsonItineraries(city: City, downloadProgressListener: DownloadProgressListener): Single<String> {
        return Single.error<String>(Throwable("not implemented,  cloud only"))
    }
}
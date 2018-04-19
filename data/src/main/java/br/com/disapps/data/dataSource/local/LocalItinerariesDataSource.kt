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

    companion object {
        private const val CODE_LINE = "codigoLinha"
        private const val CODE_STOP = "numPonto"
        private const val DIRECTION = "sentido"
        private const val SEQUENCE = "sequencia"
        private val CLAZZ = Ponto::class.java
    }

    override fun saveAllFromJson(json: String, city: City): Completable {
        val realm = database.getDatabase() as Realm
        return Completable.defer {

            try {
                realm.beginTransaction()
                realm.createOrUpdateAllFromJson(CLAZZ, json)
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
        val directions = realm.copyFromRealm(realm.where(CLAZZ)
                                                    .equalTo(CODE_LINE, codeLine)
                                                    .distinct(DIRECTION)
                                                    .findAll())
                                                    .map { it.sentido }
        realm.close()
        return Single.just(directions)
    }

    override fun getItinerary(codeLine: String, direction: String): Single<List<Ponto>> {
        val realm = database.getDatabase() as Realm
        val itinerary = realm.copyFromRealm(realm.where(CLAZZ)
                                                .equalTo(CODE_LINE, codeLine)
                                                .equalTo(DIRECTION, direction)
                                                .findAll()
                                                .sort(SEQUENCE))
        realm.close()
        return Single.just(itinerary)
    }

    override fun getAllItineraries(codeLine: String): Single<List<Ponto>> {
        val realm = database.getDatabase() as Realm

        val itinerary = realm.copyFromRealm(realm.where(CLAZZ)
                                                .equalTo(CODE_LINE, codeLine)
                                                .distinct(CODE_STOP)
                                                .findAll())

        realm.close()
        return Single.just(itinerary)
    }

    override fun jsonItineraries(city: City, downloadProgressListener: DownloadProgressListener): Single<String> {
        return Single.error<String>(Throwable("not implemented,  cloud only"))
    }
}
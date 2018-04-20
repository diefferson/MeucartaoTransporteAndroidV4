package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.ItinerariesDataSource
import br.com.disapps.data.entity.Ponto
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import io.realm.Realm

class LocalItinerariesDataSource(private val database: Database, private val preferences:Preferences) : ItinerariesDataSource {

    companion object {
        private const val CODE_LINE = "codigoLinha"
        private const val CODE_STOP = "numPonto"
        private const val DIRECTION = "sentido"
        private const val SEQUENCE = "sequencia"
        private val CLAZZ = Ponto::class.java
    }

    override suspend fun saveAllFromJson(json: String, city: City) {
        val realm = database.getDatabase() as Realm
        realm.beginTransaction()
        realm.createOrUpdateAllFromJson(CLAZZ, json)
        realm.commitTransaction()

        if(city == City.CWB){
            preferences.setCwbItinerariesDate()
        }else{
            preferences.setMetItinerariesDate()
        }
        realm.close()
    }

    override suspend fun getItineraryDirections(codeLine: String): List<String> {
        val realm = database.getDatabase() as Realm
        val directions = realm.copyFromRealm(realm.where(CLAZZ)
                                                    .equalTo(CODE_LINE, codeLine)
                                                    .distinct(DIRECTION)
                                                    .findAll())
                                                    .map { it.sentido }
        realm.close()
        return directions
    }

    override suspend fun getItinerary(codeLine: String, direction: String): List<Ponto> {
        val realm = database.getDatabase() as Realm
        val itinerary = realm.copyFromRealm(realm.where(CLAZZ)
                                                .equalTo(CODE_LINE, codeLine)
                                                .equalTo(DIRECTION, direction)
                                                .findAll()
                                                .sort(SEQUENCE))
        realm.close()
        return itinerary
    }

    override suspend fun getAllItineraries(codeLine: String): List<Ponto> {
        val realm = database.getDatabase() as Realm

        val itinerary = realm.copyFromRealm(realm.where(CLAZZ)
                                                .equalTo(CODE_LINE, codeLine)
                                                .distinct(CODE_STOP)
                                                .findAll())

        realm.close()
        return itinerary
    }

    override suspend fun jsonItineraries(city: City, downloadProgressListener: DownloadProgressListener): String {
        throw Throwable("not implemented,  cloud only")
    }
}
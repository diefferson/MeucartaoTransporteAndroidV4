package br.com.disapps.data.dataSource.local

import br.com.disapps.data.BuildConfig
import br.com.disapps.data.api.DownloadTask
import br.com.disapps.data.dataSource.ItinerariesDataSource
import br.com.disapps.data.entity.Ponto
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.data.utils.deleteFromCache
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import io.realm.Realm
import java.io.File
import java.io.FileInputStream

class LocalItinerariesDataSource(private val database: Database, private val preferences:Preferences) : ItinerariesDataSource {

    companion object {
        private const val CODE_LINE = "codigoLinha"
        private const val CODE_STOP = "numPonto"
        private const val DIRECTION = "sentido"
        private const val SEQUENCE = "sequencia"
        private val CLAZZ = Ponto::class.java
    }

    override suspend fun saveAllFromJson(filePath: String, city: City, downloadProgressListener: DownloadProgressListener) {
        val result = DownloadTask(downloadProgressListener).execute(BuildConfig.DOWNLOAD_ITINERARIES, city.toString(), filePath).get()
        if(result == "OK"){
            val realm = database.getDatabase() as Realm
            val fis = FileInputStream(File(filePath))
            realm.beginTransaction()
            realm.createOrUpdateAllFromJson(CLAZZ, fis)
            realm.commitTransaction()

            if(city == City.CWB){
                preferences.setCwbItinerariesDate()
            }else{
                preferences.setMetItinerariesDate()
            }
            realm.close()
            deleteFromCache(filePath)
        }else{
            throw KnownException(KnownError.UNKNOWN_EXCEPTION, "")
        }
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
}
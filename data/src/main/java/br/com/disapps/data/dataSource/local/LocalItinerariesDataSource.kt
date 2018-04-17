package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.ItinerariesDataSource
import br.com.disapps.data.entity.Ponto
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
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

    override fun jsonItineraries(city: City): Single<String> {
        return Single.error<String>(Throwable("not implemented,  cloud only"))
    }
}
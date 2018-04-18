package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.SchedulesDataSource
import br.com.disapps.data.entity.Horario
import br.com.disapps.data.entity.HorarioLinha
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.listeners.DownloadProgressListener
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.Realm

class LocalSchedulesDataSource(private val database: Database, private val preferences: Preferences) : SchedulesDataSource{

    override fun saveAllFromJson(json: String): Completable {
        return Completable.defer {
            val realm = database.getDatabase() as Realm
            try {
                realm.beginTransaction()
                realm.delete(HorarioLinha::class.java)
                realm.createAllFromJson(HorarioLinha::class.java, json)
                realm.commitTransaction()
                preferences.setSchedulesDate()
                Completable.complete()
            }catch (ec: Exception){
                Completable.error(ec)
            }finally {
                realm.close()
            }
        }
    }

    override fun getLineSchedulesDays(codeLine: String): Single<List<Int>> {
        val realm = database.getDatabase() as Realm
        val days = realm.copyFromRealm(realm.where(HorarioLinha::class.java)
                            .equalTo("codigoLinha", codeLine)
                            .distinct("dia")
                            .findAll()
                            .sort("dia"))
        realm.close()
        return Single.just(days.map { it.dia })
    }

    override fun getLineSchedules(codeLine: String, day: Int): Single<List<HorarioLinha>> {
        val realm = database.getDatabase() as Realm
        val schedules = realm.copyFromRealm(realm.where(HorarioLinha::class.java)
                .equalTo("codigoLinha", codeLine)
                .equalTo("dia", day)
                .findAll())
        realm.close()

        return Single.just(schedules)
    }

    override fun getAllPointSchedules(codeLine: String, day: Int, codePoint: String): Single<List<Horario>> {
        val realm = database.getDatabase() as Realm
        val schedules = realm.copyFromRealm(realm.where(HorarioLinha::class.java)
                                .equalTo("codigoLinha", codeLine)
                                .equalTo("dia", day)
                                .equalTo("numPonto", codePoint).findAll())
        realm.close()

        return Single.just(schedules.flatMap { s -> s.horarios })
    }

    override fun jsonSchedules( downloadProgressListener: DownloadProgressListener): Single<String> {
        return Single.error<String>(Throwable("not implemented,  cloud only"))
    }

}
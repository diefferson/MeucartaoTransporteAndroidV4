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

    companion object {
        private const val CODE_LINE = "codigoLinha"
        private const val DAY = "dia"
        private const val CODE_STOP = "numPonto"
        private val CLAZZ = HorarioLinha::class.java
    }

    override fun saveAllFromJson(json: String): Completable {
        return Completable.defer {
            val realm = database.getDatabase() as Realm
            try {
                realm.beginTransaction()
                realm.delete(CLAZZ)
                realm.createAllFromJson(CLAZZ, json)
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
        val days = realm.copyFromRealm(realm.where(CLAZZ)
                                            .equalTo(CODE_LINE, codeLine)
                                            .distinct(DAY)
                                            .findAll()
                                            .sort(DAY))
        realm.close()
        return Single.just(days.map { it.dia })
    }

    override fun getLineSchedules(codeLine: String, day: Int): Single<List<HorarioLinha>> {
        val realm = database.getDatabase() as Realm
        val schedules = realm.copyFromRealm(realm.where(CLAZZ)
                                                .equalTo(CODE_LINE, codeLine)
                                                .equalTo(DAY, day)
                                                .findAll())
        realm.close()

        return Single.just(schedules)
    }

    override fun getAllPointSchedules(codeLine: String, day: Int, codePoint: String): Single<List<Horario>> {
        val realm = database.getDatabase() as Realm
        val schedules = realm.copyFromRealm(realm.where(CLAZZ)
                                                .equalTo(CODE_LINE, codeLine)
                                                .equalTo(DAY, day)
                                                .equalTo(CODE_STOP, codePoint)
                                                .findAll())
                                                .flatMap { it.horarios }
        realm.close()

        return Single.just(schedules)
    }

    override fun jsonSchedules( downloadProgressListener: DownloadProgressListener): Single<String> {
        return Single.error<String>(Throwable("not implemented,  cloud only"))
    }
}
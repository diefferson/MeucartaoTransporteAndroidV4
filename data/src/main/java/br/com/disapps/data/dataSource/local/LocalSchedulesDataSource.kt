package br.com.disapps.data.dataSource.local

import android.content.res.AssetManager
import br.com.disapps.data.BuildConfig
import br.com.disapps.data.api.DownloadTask
import br.com.disapps.data.dataSource.SchedulesDataSource
import br.com.disapps.data.entity.Horario
import br.com.disapps.data.entity.HorarioLinha
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.data.utils.deleteFromCache
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.listeners.DownloadProgressListener
import io.realm.Realm
import java.io.File
import java.io.FileInputStream

class LocalSchedulesDataSource(private val database: Database, private val preferences: Preferences, private val assetManager: AssetManager) : SchedulesDataSource{

    companion object {
        private const val CODE_LINE = "codigoLinha"
        private const val DAY = "dia"
        private const val CODE_STOP = "numPonto"
        private val CLAZZ = HorarioLinha::class.java
    }

    override suspend fun saveAllFromJson(filePath: String, downloadProgressListener: DownloadProgressListener) {
        val result = DownloadTask(downloadProgressListener).execute(BuildConfig.DOWNLOAD_SCHEDULES,"", filePath).get()
        if(result == "OK"){
            val realm = database.getDatabase() as Realm
            val fis = FileInputStream(File(filePath))
            realm.beginTransaction()
            realm.delete(CLAZZ)
            realm.createAllFromJson(CLAZZ, fis)
            realm.commitTransaction()
            preferences.setSchedulesDate()
            realm.close()
            deleteFromCache(filePath)
        }else{
            throw KnownException(KnownError.UNKNOWN_EXCEPTION, "")
        }
    }

    override suspend fun getLineSchedulesDays(codeLine: String): List<Int> {
        val realm = database.getDatabase() as Realm
        val days = realm.copyFromRealm(realm.where(CLAZZ)
                                            .equalTo(CODE_LINE, codeLine)
                                            .distinct(DAY)
                                            .findAll()
                                            .sort(DAY))
        realm.close()
        return days.map { it.dia }
    }

    override suspend fun getLineSchedules(codeLine: String, day: Int): List<HorarioLinha> {
        val realm = database.getDatabase() as Realm
        val schedules = realm.copyFromRealm(realm.where(CLAZZ)
                                                .equalTo(CODE_LINE, codeLine)
                                                .equalTo(DAY, day)
                                                .findAll())
        realm.close()

        return schedules
    }

    override suspend fun getAllPointSchedules(codeLine: String, day: Int, codePoint: String): List<Horario> {
        val realm = database.getDatabase() as Realm
        val schedules = realm.copyFromRealm(realm.where(CLAZZ)
                                                .equalTo(CODE_LINE, codeLine)
                                                .equalTo(DAY, day)
                                                .equalTo(CODE_STOP, codePoint)
                                                .findAll())
                                                .flatMap { it.horarios }
        realm.close()
        return schedules
    }
}
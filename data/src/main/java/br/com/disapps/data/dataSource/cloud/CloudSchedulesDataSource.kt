package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.SchedulesDataSource
import br.com.disapps.data.entity.Horario
import br.com.disapps.data.entity.HorarioLinha
import br.com.disapps.data.entity.HorarioLinhaF
import br.com.disapps.data.entity.mappers.toHorarioLinha
import br.com.disapps.data.storage.database.readList
import br.com.disapps.domain.listeners.DownloadProgressListener
import com.google.firebase.database.FirebaseDatabase

class CloudSchedulesDataSource(private val restApi: RestApi) : SchedulesDataSource{

    override suspend fun saveAllFromJson(filePath: String, downloadProgressListener: DownloadProgressListener) {
        throw Throwable("not implemented, only local")
    }

    override suspend fun saveAllFromJson(filePath: String) {
        throw Throwable("not implemented, only local")
    }

    override suspend fun getLineSchedulesDays(codeLine: String): List<Int> {
        val database = FirebaseDatabase.getInstance()
        val schedulesNode = database.getReference(REFERENCE).equalTo(CODE_LINE, codeLine).ref
        val teste =  schedulesNode.readList<HorarioLinhaF>()
        val teste2 = teste.map { it.dia }
        val teste3 = teste2.distinct()
        return  teste3
    }

    override suspend fun getLineSchedules(codeLine: String, day: Int): List<HorarioLinha> {
        val database = FirebaseDatabase.getInstance()
        val schedulesNode = database.getReference(REFERENCE)
                .equalTo(CODE_LINE, codeLine)
                .equalTo(DAY, day.toString())
                .ref
        val teste = schedulesNode.readList<HorarioLinhaF>()
        return teste.toHorarioLinha()
    }

    override suspend fun getAllPointSchedules(codeLine: String, day: Int, codePoint: String): List<Horario> {
        throw Throwable("not implemented, only local")
    }

    companion object {
        private const val REFERENCE = "horarios"
        const val CODE_LINE = "codigoLinha"
        const val DAY = "dia"
        const val CODE_STOP = "numPonto"
    }
}
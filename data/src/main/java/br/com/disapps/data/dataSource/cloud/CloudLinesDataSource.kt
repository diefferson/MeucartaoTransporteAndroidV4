package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.LinesDataSource
import br.com.disapps.data.entity.Linha
import br.com.disapps.data.storage.database.readList
import br.com.disapps.data.storage.database.readValue
import br.com.disapps.domain.listeners.DownloadProgressListener
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by dnso on 15/03/2018.
 */
class CloudLinesDataSource(private val restApi: RestApi) : LinesDataSource {

    override suspend fun saveAllLinesFromJson(filePath: String, downloadProgressListener: DownloadProgressListener) {
        throw Throwable("not implemented, only local")
    }

    override suspend fun saveAllLinesFromJson(filePath: String) {
        throw Throwable("not implemented, only local")
    }

    override suspend fun saveLine(linha: Linha) {
        throw Throwable("not implemented, only local")
    }

    override suspend fun lines(): List<Linha> {
        val database = FirebaseDatabase.getInstance()
        val linesNode = database.getReference(REFERENCE)
        return linesNode.readList()
    }

    override suspend fun line(linha: Linha): Linha {
        val database = FirebaseDatabase.getInstance()
        val linesNode = database.getReference(REFERENCE).equalTo(CODE, linha.codigo).ref
        return linesNode.readValue()
    }

    override suspend fun updateLine(linha: Linha) {
        throw Throwable("not implemented, only local")
    }

    companion object {
        private const val REFERENCE = "linhas"
        private const val CODE = "codigo"
    }
}
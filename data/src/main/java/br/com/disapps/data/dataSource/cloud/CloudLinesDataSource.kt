package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.LinesDataSource
import br.com.disapps.data.entity.Linha
import br.com.disapps.domain.listeners.DownloadProgressListener

/**
 * Created by dnso on 15/03/2018.
 */
class CloudLinesDataSource(private val restApi: RestApi) : LinesDataSource {

    override suspend fun saveAllLinesFromJson(filePath: String, downloadProgressListener: DownloadProgressListener) {
        throw Throwable("not implemented, only local")
    }

    override suspend fun saveLine(linha: Linha) {
        throw Throwable("not implemented, only local")
    }

    override suspend fun lines(): List<Linha> {
        throw Throwable("not implemented, only local")
    }

    override suspend fun line(linha: Linha): Linha {
        throw Throwable("not implemented, only local")
    }

    override suspend fun updateLine(linha: Linha) {
        throw Throwable("not implemented, only local")
    }
}
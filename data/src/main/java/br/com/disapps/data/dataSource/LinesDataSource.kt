package br.com.disapps.data.dataSource

import br.com.disapps.data.entity.Linha
import br.com.disapps.domain.listeners.DownloadProgressListener

interface LinesDataSource : DataSource {

    suspend fun saveLine(linha : Linha)

    suspend fun saveAllLinesFromJson(filePath:String, downloadProgressListener: DownloadProgressListener)

    suspend fun lines() : List<Linha>

    suspend fun line(linha: Linha) : Linha

    suspend fun updateLine(linha: Linha)
}
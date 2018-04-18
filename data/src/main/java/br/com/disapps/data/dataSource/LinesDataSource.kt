package br.com.disapps.data.dataSource

import br.com.disapps.data.entity.Linha
import br.com.disapps.data.dataSource.DataSource
import br.com.disapps.domain.listeners.DownloadProgressListener
import io.reactivex.Completable
import io.reactivex.Single

interface LinesDataSource : DataSource {

    fun saveLine(linha : Linha): Completable

    fun saveAllFromJson(json : String): Completable

    fun lines() : Single<List<Linha>>

    fun jsonLines(downloadProgressListener: DownloadProgressListener) : Single<String>

    fun line(linha: Linha) : Single<Linha>

    fun updateLine(linha: Linha): Completable
}
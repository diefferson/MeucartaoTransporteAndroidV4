package br.com.disapps.domain.repository

import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.Line
import io.reactivex.Completable
import io.reactivex.Single


interface LinesRepository{

    fun saveLine(line : Line): Completable

    fun saveAllLinesFromJson(json : String): Completable

    fun jsonLines(downloadProgressListener: DownloadProgressListener) : Single<String>

    fun lines() : Single<List<Line>>

    fun line(line: Line) : Single<Line>

    fun updateLine(line : Line): Completable

}
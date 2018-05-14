package br.com.disapps.domain.repository

import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.Line


interface LinesRepository{

    suspend fun saveLine(line : Line)

    suspend fun saveAllLinesFromJson(json : String)

    suspend fun jsonLines(downloadProgressListener: DownloadProgressListener) : String

    suspend fun lines() : List<Line>

    suspend fun line(line: Line) : Line

    suspend fun updateLine(line : Line)

    suspend fun initLines()

}
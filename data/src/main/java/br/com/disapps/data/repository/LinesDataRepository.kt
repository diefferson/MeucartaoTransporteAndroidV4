package br.com.disapps.data.repository

import br.com.disapps.data.entity.mappers.toLineBO
import br.com.disapps.data.entity.mappers.toLineDTO
import br.com.disapps.data.dataSource.factory.LinesDataSourceFactory
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by dnso on 15/03/2018.
 */
class LinesDataRepository(private val linesDataSourceFactory: LinesDataSourceFactory) : LinesRepository{

    override suspend fun saveLine(line: Line) {
        return linesDataSourceFactory
                .create()
                .saveLine(line.toLineDTO())
    }

    override suspend fun saveAllLinesFromJson(json: String) {
        return linesDataSourceFactory
                .create()
                .saveAllFromJson(json)
    }

    override suspend fun lines(): List<Line> {
        return linesDataSourceFactory
                .create()
                .lines()
                .map{ it.toLineBO() }
    }

    override suspend fun line(line : Line): Line{
        return linesDataSourceFactory
                .create()
                .line(line.toLineDTO())
                .toLineBO()
    }

    override suspend fun jsonLines(downloadProgressListener: DownloadProgressListener): String {
        return linesDataSourceFactory
                .create(true)
                .jsonLines(downloadProgressListener)
    }

    override suspend fun updateLine(line: Line) {
        return linesDataSourceFactory
                .create()
                .updateLine(line.toLineDTO())
    }

    override suspend fun initLines() {
        return linesDataSourceFactory.create().initLines()
    }
}
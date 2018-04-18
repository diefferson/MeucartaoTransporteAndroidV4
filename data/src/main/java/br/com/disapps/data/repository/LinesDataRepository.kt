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

    override fun saveLine(line: Line): Completable {
        return linesDataSourceFactory
                .create()
                .saveLine(line.toLineDTO())
    }

    override fun saveAllLinesFromJson(json: String): Completable {
        return linesDataSourceFactory
                .create()
                .saveAllFromJson(json)
    }

    override fun lines(): Single<List<Line>> {
        return linesDataSourceFactory
                .create()
                .lines()
                .map{ l -> l.toLineBO() }
    }

    override fun line(line : Line): Single<Line> {
        return linesDataSourceFactory
                .create()
                .line(line.toLineDTO())
                .map{ l -> l.toLineBO() }
    }

    override fun jsonLines(downloadProgressListener: DownloadProgressListener): Single<String> {
        return linesDataSourceFactory
                .create(true)
                .jsonLines(downloadProgressListener)
    }

    override fun updateLine(line: Line): Completable {
        return linesDataSourceFactory
                .create()
                .updateLine(line.toLineDTO())
    }
}
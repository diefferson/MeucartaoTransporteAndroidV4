package br.com.disapps.data.repository

import br.com.disapps.data.entity.mappers.toLineBO
import br.com.disapps.data.entity.mappers.toLineDTO
import br.com.disapps.data.repository.dataSource.lines.LinesDataSourceFactory
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
class LinesDataRepository(private var linesDataSourceFactory: LinesDataSourceFactory) : LinesRepository{

    override fun saveLine(line: Line):Observable<Boolean> {
        return linesDataSourceFactory
                .create()
                .saveLine(line.toLineDTO())
    }

    override fun saveAllFromJson(json: String):Observable<Boolean> {
        return linesDataSourceFactory
                .create()
                .saveAllFromJson(json)
    }

    override fun lines(): Observable<List<Line>> {
        return linesDataSourceFactory
                .create()
                .lines()
                .map{ l -> l.toLineBO() }
    }

    override fun line(line : Line): Observable<Line> {
        return linesDataSourceFactory
                .create()
                .line(line.toLineDTO())
                .map{ l -> l.toLineBO() }
    }

    override fun jsonLines(): Observable<String> {
        return linesDataSourceFactory
                .create(true)
                .jsonLines()
    }

    override fun updateLine(line: Line): Observable<Boolean> {
        return linesDataSourceFactory
                .create()
                .updateLine(line.toLineDTO())
    }
}
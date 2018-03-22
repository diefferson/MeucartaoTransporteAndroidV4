package br.com.disapps.data.repository

import br.com.disapps.data.entity.mappers.LineEntityMapper
import br.com.disapps.data.repository.dataSource.lines.LinesDataSourceFactory
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
class LinesDataRepository(private var linesDataSourceFactory: LinesDataSourceFactory) : LinesRepository{

    override fun saveLine(line: Line) {
        linesDataSourceFactory
                .create()
                .saveLine(LineEntityMapper.mapToEntity(line))
    }

    override fun saveAllFromJson(json: String) {
        linesDataSourceFactory
                .create()
                .saveAllFromJson(json)
    }

    override fun lines(): Observable<List<Line>> {
        return linesDataSourceFactory
                .create()
                .lines()
                .map(LineEntityMapper::mapFromEntity)
    }

    override fun line(line : Line): Observable<Line> {
        return linesDataSourceFactory
                .create()
                .line(LineEntityMapper.mapToEntity(line))
                .map(LineEntityMapper::mapFromEntity)
    }

    override fun jsonLines(): Observable<String> {
        return linesDataSourceFactory
                .create(true)
                .jsonLines()
    }
}
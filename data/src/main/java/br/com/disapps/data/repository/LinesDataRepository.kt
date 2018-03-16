package br.com.disapps.data.repository

import br.com.disapps.data.entity.mapper.LineEntityMapper
import br.com.disapps.data.repository.dataSource.lines.LinesDataSourceFactory
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
class LinesDataRepository(private var linesDataSourceFactory: LinesDataSourceFactory, private var lineEntityMapper: LineEntityMapper) : LinesRepository{

    override fun saveLine(line: Line) {
        linesDataSourceFactory
                .create()
                .saveLine(lineEntityMapper.mapToEntity(line))
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
                .map(lineEntityMapper::mapFromEntity)
    }

    override fun line(): Observable<Line> {
        return linesDataSourceFactory
                .create()
                .line()
                .map(lineEntityMapper::mapFromEntity)
    }

    override fun jsonLines(): Observable<String> {
        return linesDataSourceFactory
                .create(true)
                .jsonLines()
    }
}
package br.com.disapps.data.repository

import br.com.disapps.data.entity.Linha
import br.com.disapps.data.repository.dataSource.lines.LinesDataSourceFactory
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
class LinesDataRepository(private var linesDataSourceFactory: LinesDataSourceFactory) : LinesRepository{

    override fun saveLine(line: Line) {
        linesDataSourceFactory.create(false).saveLine(Linha())
    }

    override fun saveAllFromJson(json: String) {
        linesDataSourceFactory.create(false).saveAllFromJson(json)
    }

    override fun lines(): Observable<List<Line>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun line(): Observable<Line> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun jsonLines(): Observable<String> {
        return linesDataSourceFactory.create(true).jsonLines()
    }
}
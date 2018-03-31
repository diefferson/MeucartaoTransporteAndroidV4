package br.com.disapps.data.repository.dataSource.lines

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.entity.Linha
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
class CloudLinesDataSource(private var restApi: RestApi) : LinesDataSource{

    override fun saveLine(linha: Linha):Observable<Boolean> {
        TODO("not implemented, local only")
    }

    override fun saveAllFromJson(json: String) :Observable<Boolean>{
        TODO("not implemented, local only")
    }

    override fun lines(): Observable<List<Linha>> {
        TODO("not implemented, local only")
    }

    override fun line(linha: Linha): Observable<Linha> {
        TODO("not implemented, local only")
    }

    override fun updateLine(linha: Linha): Observable<Boolean> {
        TODO("not implemented, local only")
    }

    override fun jsonLines(): Observable<String> {
        return restApi.listaLinhas().map { t -> t.toString() }
    }
}
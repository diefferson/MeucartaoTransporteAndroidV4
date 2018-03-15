package br.com.disapps.data.repository.dataSource.lines

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.entity.Linha
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
class CloudLinesDataSource(private var restApi: RestApi) : LinesDataSource{

    override fun saveLine(linha: Linha) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveAllFromJson(json: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lines(): Observable<List<Linha>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.0
    }

    override fun line(): Observable<Linha> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun jsonLines(): Observable<String> {
        return restApi.listaLinhas().map { t -> t.toString() }
    }
}
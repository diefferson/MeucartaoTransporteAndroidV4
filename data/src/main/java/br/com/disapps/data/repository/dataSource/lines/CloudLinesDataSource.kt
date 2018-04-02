package br.com.disapps.data.repository.dataSource.lines

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.entity.Linha
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by dnso on 15/03/2018.
 */
class CloudLinesDataSource(private var restApi: RestApi) : LinesDataSource{

    override fun saveLine(linha: Linha): Completable {
        TODO("not implemented, local only")
    }

    override fun saveAllFromJson(json: String) :Completable{
        TODO("not implemented, local only")
    }

    override fun lines(): Single<List<Linha>> {
        TODO("not implemented, local only")
    }

    override fun line(linha: Linha): Single<Linha> {
        TODO("not implemented, local only")
    }

    override fun updateLine(linha: Linha): Completable {
        TODO("not implemented, local only")
    }

    override fun jsonLines(): Single<String> {
        return restApi.listaLinhas().map { t -> t.toString() }
    }
}
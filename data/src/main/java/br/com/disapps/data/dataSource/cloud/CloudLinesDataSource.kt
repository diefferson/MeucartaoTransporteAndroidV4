package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.DownloadClient.getRetrofitDownloadClient
import br.com.disapps.data.api.RestApi
import br.com.disapps.data.entity.Linha
import br.com.disapps.data.dataSource.LinesDataSource
import br.com.disapps.domain.listeners.DownloadProgressListener
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by dnso on 15/03/2018.
 */
class CloudLinesDataSource(private val restApi: RestApi) : LinesDataSource {

    override fun jsonLines(downloadProgressListener: DownloadProgressListener): Single<String> {
        return getRetrofitDownloadClient(downloadProgressListener)
                .listaLinhas().map { it.toString() }
    }

    override fun saveLine(linha: Linha): Completable {
        return Completable.error(Throwable("not implemented, only local"))
    }

    override fun saveAllFromJson(json: String) :Completable{
        return Completable.error(Throwable("not implemented, only local"))
    }

    override fun lines(): Single<List<Linha>> {
        return Single.error<List<Linha>>(Throwable("not implemented, only local"))
    }

    override fun line(linha: Linha): Single<Linha> {
        return Single.error<Linha>(Throwable("not implemented, only local"))
    }

    override fun updateLine(linha: Linha): Completable {
        return Completable.error(Throwable("not implemented, only local"))
    }

}
package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.Extrato
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.data.dataSource.CardsDataSource
import br.com.disapps.domain.interactor.base.CompletableCallback
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by dnso on 16/03/2018.
 */
class CloudCardsDataSource(private val restApi: RestApi) : CardsDataSource {

    override fun card(requestCartao: RequestCartao): Single<Cartao?> {
        return restApi.saldoCartao(requestCartao.codigo, requestCartao.cpf, requestCartao.tipoConsulta).map { t -> t.content }
    }

    override fun getExtract(requestCartao: RequestCartao): Single<List<Extrato>> {
        return restApi.extratoCartao(requestCartao.codigo, requestCartao.cpf, requestCartao.tipoConsulta).map { t -> t.content }
    }

    override fun hasCard(cartao: Cartao): Single<Boolean> {
        return Single.error<Boolean>(Throwable("not implemented, only local"))
    }

    override fun deleteCard( cartao: Cartao, callback: CompletableCallback){
        return callback.onError(Throwable("not implemented, only local"))
    }

    override fun saveCard(cartao: Cartao, callback: CompletableCallback){
        return callback.onError(Throwable("not implemented, only local"))
    }

    override fun cards(): Single<List<Cartao>> {
        return Single.error<List<Cartao>>(Throwable("not implemented, only local"))
    }
}
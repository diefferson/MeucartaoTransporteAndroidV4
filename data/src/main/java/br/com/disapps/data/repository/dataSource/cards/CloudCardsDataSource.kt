package br.com.disapps.data.repository.dataSource.cards

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.Extrato
import br.com.disapps.data.entity.RequestCartao
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by dnso on 16/03/2018.
 */
class CloudCardsDataSource(private var restApi: RestApi) : CardsDataSource{

    override fun saveCard(cartao: Cartao): Completable {
        TODO("not implemented, only local")
    }

    override fun cards(): Single<List<Cartao>> {
        TODO("not implemented, only local")
    }

    override fun card(requestCartao: RequestCartao): Single<Cartao?> {
        return restApi.saldoCartao(requestCartao.codigo, requestCartao.cpf, requestCartao.tipoConsulta).map { t -> t.content }
    }

    override fun hasCard(cartao: Cartao): Single<Boolean> {
        TODO("not implemented, only local")
    }

    override fun deleteCard(cartao: Cartao): Completable {
        TODO("not implemented, only local")
    }

    override fun getExtract(requestCartao: RequestCartao): Single<List<Extrato>> {
        return restApi.extratoCartao(requestCartao.codigo, requestCartao.cpf, requestCartao.tipoConsulta).map { t -> t.content }
    }
}
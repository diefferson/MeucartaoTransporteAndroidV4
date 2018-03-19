package br.com.disapps.data.repository.dataSource.cards

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.RequestCartao
import io.reactivex.Observable

/**
 * Created by dnso on 16/03/2018.
 */
class CloudCardsDataSource(private var restApi: RestApi) : CardsDataSource{

    override fun saveCard(cartao: Cartao) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cards(): Observable<List<Cartao>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun card(requestCartao: RequestCartao): Observable<Cartao> {
        return restApi.saldoCartao(requestCartao.codigo, requestCartao.cpf, requestCartao.tipoConsulta).map { t -> t.content }
    }
}
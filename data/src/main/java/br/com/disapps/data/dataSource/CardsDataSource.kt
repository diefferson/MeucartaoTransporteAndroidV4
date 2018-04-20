package br.com.disapps.data.dataSource

import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.Extrato
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.domain.interactor.base.CompletableCallback
import io.reactivex.Single

interface CardsDataSource : DataSource {

    fun saveCard( cartao: Cartao, callback:CompletableCallback)

    fun deleteCard(cartao: Cartao, callback:CompletableCallback)

    fun cards() : Single<List<Cartao>>

    fun card(requestCartao: RequestCartao) : Single<Cartao?>

    fun hasCard(cartao: Cartao) : Single<Boolean>

    fun getExtract(requestCartao: RequestCartao) : Single<List<Extrato>>
}
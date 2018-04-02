package br.com.disapps.data.repository.dataSource.cards

import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.Extrato
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.data.repository.dataSource.DataSource
import io.reactivex.Completable
import io.reactivex.Single


interface CardsDataSource : DataSource {

    fun saveCard(cartao: Cartao): Completable

    fun deleteCard(cartao: Cartao): Completable

    fun cards() : Single<List<Cartao>>

    fun card(requestCartao: RequestCartao) : Single<Cartao?>

    fun hasCard(cartao: Cartao) : Single<Boolean>

    fun getExtract(requestCartao: RequestCartao) : Single<List<Extrato>>
}
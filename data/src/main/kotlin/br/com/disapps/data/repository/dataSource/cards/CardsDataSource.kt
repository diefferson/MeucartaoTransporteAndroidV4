package br.com.disapps.data.repository.dataSource.cards

import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.Extrato
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.data.repository.dataSource.DataSource
import io.reactivex.Observable


interface CardsDataSource : DataSource {

    fun saveCard(cartao: Cartao): Observable<Boolean>

    fun deleteCard(cartao: Cartao): Observable<Boolean>

    fun cards() : Observable<List<Cartao>>

    fun card(requestCartao: RequestCartao) : Observable<Cartao?>

    fun hasCard(cartao: Cartao) : Observable<Boolean>

    fun getExtract(requestCartao: RequestCartao) : Observable<List<Extrato>>
}
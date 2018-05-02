package br.com.disapps.data.dataSource

import br.com.disapps.data.entity.*

interface CardsDataSource : DataSource {

    suspend fun saveCard( cartao: Cartao)

    suspend fun deleteCard(cartao: Cartao)

    suspend fun cards() : List<Cartao>

    suspend fun card(requestCartao: RequestCartao) : Cartao?

    suspend fun hasCard(cartao: Cartao) : Boolean

    suspend fun getExtract(requestCartao: RequestCartao) : List<Extrato>?
}
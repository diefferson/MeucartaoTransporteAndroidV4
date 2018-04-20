package br.com.disapps.domain.repository

import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract

interface CardsRepository{

    suspend fun saveCard(card : Card)

    suspend fun deleteCard( card : Card)

    suspend fun cards() : List<Card>

    suspend fun card(card : Card) : Card?

    suspend fun hasCard(card : Card) : Boolean

    suspend fun extract(card: Card) : List<Extract>?
}
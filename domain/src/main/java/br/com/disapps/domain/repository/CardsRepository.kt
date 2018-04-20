package br.com.disapps.domain.repository

import br.com.disapps.domain.interactor.base.CompletableCallback
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import io.reactivex.Single

interface CardsRepository{

    fun saveCard(card : Card, callback:CompletableCallback)

    fun deleteCard( card : Card, callback:CompletableCallback)

    fun cards() : Single<List<Card>>

    fun card(card : Card) : Single<Card?>

    fun hasCard(card : Card) : Single<Boolean>

    fun extract(card: Card) : Single<List<Extract>>
}
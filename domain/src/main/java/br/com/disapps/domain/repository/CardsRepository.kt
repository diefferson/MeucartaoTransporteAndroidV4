package br.com.disapps.domain.repository

import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import io.reactivex.Completable
import io.reactivex.Single

interface CardsRepository{

    fun saveCard(card : Card) : Completable

    fun deleteCard(card : Card) : Completable

    fun cards() : Single<List<Card>>

    fun card(card : Card) : Single<Card?>

    fun hasCard(card : Card) : Single<Boolean>

    fun extract(card: Card) : Single<List<Extract>>
}
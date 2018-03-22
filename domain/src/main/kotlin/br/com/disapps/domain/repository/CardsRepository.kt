package br.com.disapps.domain.repository

import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import io.reactivex.Observable

interface CardsRepository{

    fun saveCard(card : Card) : Observable<Boolean>

    fun deleteCard(card : Card) : Observable<Boolean>

    fun cards() : Observable<List<Card>>

    fun card(card : Card) : Observable<Card?>

    fun hasCard(card : Card) : Observable<Boolean>

    fun getExtract(card: Card) : Observable<List<Extract>>
}
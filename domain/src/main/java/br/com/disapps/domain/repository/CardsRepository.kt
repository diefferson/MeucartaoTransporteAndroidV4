package br.com.disapps.domain.repository

import br.com.disapps.domain.model.Card
import io.reactivex.Observable

/**
 * Interface that represents a Repository for getting {@link Card} related data.
 */
interface CardsRepository{

    /**
     * Save an {@link Card} in storage
     *
     * @param {@link Card} Card object to save on storage.
     */
    fun saveCard(card : Card)

    /**
     * Get an {@link Observable} which will emit a List of {@link Card}.
     */
    fun cards() : Observable<List<Card>>

    /**
     * Get an {@link Observable} which will emit a {@link Card}.
     *
     * @param code The code of card used to retrieve card data.
     */
    fun card(card : Card) : Observable<Card>

}
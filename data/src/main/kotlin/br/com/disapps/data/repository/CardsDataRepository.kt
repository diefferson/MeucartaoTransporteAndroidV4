package br.com.disapps.data.repository

import br.com.disapps.data.entity.mappers.*
import br.com.disapps.data.repository.dataSource.cards.CardsDataSourceFactory
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
class CardsDataRepository( private var cardsDataSourceFactory: CardsDataSourceFactory) : CardsRepository {

    override fun saveCard(card: Card): Observable<Boolean> {
        return cardsDataSourceFactory
                .create()
                .saveCard(card.toCardDTO())
    }

    override fun cards(): Observable<List<Card>> {
        return cardsDataSourceFactory
                .create()
                .cards()
                .map{ c -> c.toCardBO()}
    }

    override fun card(card: Card): Observable<Card?> {
        return cardsDataSourceFactory
                .create(true)
                .card(card.toRequestCardDTO())
                .map{ c -> c.toCardBO() }
    }

    override fun hasCard(card: Card): Observable<Boolean> {
        return cardsDataSourceFactory
                .create()
                .hasCard(card.toCardDTO())
    }

    override fun deleteCard(card: Card): Observable<Boolean> {
        return cardsDataSourceFactory
                .create()
                .deleteCard(card.toCardDTO())
    }

    override fun getExtract(card: Card): Observable<List<Extract>> {
        return cardsDataSourceFactory
                .create(true)
                .getExtract(card.toRequestExtractDTO())
                .map { e -> e.toExtractBO() }
    }
}
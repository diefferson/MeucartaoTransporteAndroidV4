package br.com.disapps.data.repository

import br.com.disapps.data.entity.mappers.*
import br.com.disapps.data.repository.dataSource.cards.CardsDataSourceFactory
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by dnso on 15/03/2018.
 */
class CardsDataRepository( private var cardsDataSourceFactory: CardsDataSourceFactory) : CardsRepository {

    override fun saveCard(card: Card): Completable {
        return cardsDataSourceFactory
                .create()
                .saveCard(card.toCardDTO())
    }

    override fun cards(): Single<List<Card>> {
        return cardsDataSourceFactory
                .create()
                .cards()
                .map{ c -> c.toCardBO()}
    }

    override fun card(card: Card): Single<Card?> {
        return cardsDataSourceFactory
                .create(true)
                .card(card.toRequestCardDTO())
                .map{ c -> c.toCardBO() }
    }

    override fun hasCard(card: Card): Single<Boolean> {
        return cardsDataSourceFactory
                .create()
                .hasCard(card.toCardDTO())
    }

    override fun deleteCard(card: Card): Completable {
        return cardsDataSourceFactory
                .create()
                .deleteCard(card.toCardDTO())
    }

    override fun extract(card: Card): Single<List<Extract>> {
        return cardsDataSourceFactory
                .create(true)
                .getExtract(card.toRequestExtractDTO())
                .map { e -> e.toExtractBO() }
    }
}
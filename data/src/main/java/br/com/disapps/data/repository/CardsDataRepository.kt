package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.CardsDataSourceFactory
import br.com.disapps.data.entity.mappers.*
import br.com.disapps.domain.interactor.base.CompletableCallback
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Single

/**
 * Created by dnso on 15/03/2018.
 */
class CardsDataRepository( private val cardsDataSourceFactory: CardsDataSourceFactory) : CardsRepository {

    override fun saveCard( card: Card, callback: CompletableCallback){
        return cardsDataSourceFactory
                .create()
                .saveCard( card.toCardDTO(), callback)
    }

    override fun deleteCard( card: Card, callback: CompletableCallback){
        return cardsDataSourceFactory
                .create()
                .deleteCard(card.toCardDTO(), callback)
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
                .map{ it.toCardBO() }
    }

    override fun hasCard(card: Card): Single<Boolean> {
        return cardsDataSourceFactory
                .create()
                .hasCard(card.toCardDTO())
    }


    override fun extract(card: Card): Single<List<Extract>> {
        return cardsDataSourceFactory
                .create(true)
                .getExtract(card.toRequestExtractDTO())
                .map { it.toExtractBO() }
    }
}
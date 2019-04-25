package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.CardsDataSourceFactory
import br.com.disapps.data.entity.mappers.*
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.domain.repository.CardsRepository

/**
 * Created by dnso on 15/03/2018.
 */
class CardsDataRepository( private val cardsDataSourceFactory: CardsDataSourceFactory) : CardsRepository {

    override suspend fun saveCard( card: Card){
        return cardsDataSourceFactory
                .create()
                .saveCard( card.toCardDTO())
    }

    override suspend fun updateCard( card: Card){
        return cardsDataSourceFactory
                .create()
                .updateCard( card.toCardDTO())
    }

    override suspend fun deleteCard( card: Card){
        return cardsDataSourceFactory
                .create()
                .deleteCard(card.toCardDTO())
    }

    override suspend fun cards(): List<Card> {
        return cardsDataSourceFactory
                .create()
                .cards()
                .map{ c -> c.toCardBO()}
    }

    override suspend fun card(card: Card): Card? {
        return cardsDataSourceFactory
                .create(true)
                .card(card.toRequestCardDTO())?.toCardBO()
    }

    override suspend fun hasCard(card: Card): Boolean {
        return cardsDataSourceFactory
                .create()
                .hasCard(card.toCardDTO())
    }


    override suspend fun extract(card: Card): List<Extract>? {
        return cardsDataSourceFactory
                .create(true)
                .getExtract(card.toRequestExtractDTO())?.toExtractBO()

    }

    override suspend fun getPassValue(): Float {
        return  cardsDataSourceFactory.create(true).getPassValue()
    }
}
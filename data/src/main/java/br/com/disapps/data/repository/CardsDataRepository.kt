package br.com.disapps.data.repository

import br.com.disapps.data.entity.mappers.CardEntityMapper
import br.com.disapps.data.entity.mappers.CardRequestMapper
import br.com.disapps.data.repository.dataSource.cards.CardsDataSourceFactory
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
class CardsDataRepository( private var cardsDataSourceFactory: CardsDataSourceFactory) : CardsRepository {

    override fun saveCard(card: Card): Observable<Boolean> {
        return cardsDataSourceFactory
                .create()
                .saveCard(CardEntityMapper.mapToEntity(card))
    }

    override fun cards(): Observable<List<Card>> {
        return cardsDataSourceFactory
                .create()
                .cards()
                .map(CardEntityMapper::mapFromEntity)
    }

    override fun card(card: Card): Observable<Card?> {
        return cardsDataSourceFactory
                .create(true)
                .card(CardRequestMapper.mapToEntity(card))
                .map(CardEntityMapper::mapFromEntity)
    }

    override fun hasCard(card: Card): Observable<Boolean> {
        return cardsDataSourceFactory
                .create()
                .hasCard(CardEntityMapper.mapToEntity(card))
    }

    override fun deleteCard(card: Card): Observable<Boolean> {
        return cardsDataSourceFactory
                .create()
                .deleteCard(CardEntityMapper.mapToEntity(card))
    }
}
package br.com.disapps.data.repository

import br.com.disapps.data.entity.mapper.CardEntityMapper
import br.com.disapps.data.entity.mapper.CardRequestMapper
import br.com.disapps.data.repository.dataSource.cards.CardsDataSourceFactory
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
class CardsDataRepository( private var cardsDataSourceFactory: CardsDataSourceFactory,
                          private var cardEntityMapper: CardEntityMapper,
                          private var cardRequestMapper: CardRequestMapper) : CardsRepository {

    override fun saveCard(card: Card) {
        cardsDataSourceFactory
                .create()
                .saveCard(cardEntityMapper.mapToEntity(card))
    }

    override fun cards(): Observable<List<Card>> {
        return cardsDataSourceFactory
                .create()
                .cards()
                .map(cardEntityMapper::mapFromEntity)
    }

    override fun card(card: Card): Observable<Card> {
        return cardsDataSourceFactory
                .create(true)
                .card(cardRequestMapper.mapToEntity(card))
                .map(cardEntityMapper::mapFromEntity)
    }
}
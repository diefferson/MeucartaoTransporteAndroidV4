package br.com.disapps.data.repository

import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.mapper.CardEntityMapper
import br.com.disapps.data.repository.dataSource.cards.CardsDataSourceFactory
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Observable

/**
 * Created by dnso on 15/03/2018.
 */
class CardsDataRepository(private var cardsDataSourceFactory: CardsDataSourceFactory, private var cardEntityMapper: CardEntityMapper) : CardsRepository {

    override fun saveCard(card: Card) {
        cardsDataSourceFactory.create().saveCard(Cartao())
    }

    override fun cards(): Observable<List<Card>> {
        return cardsDataSourceFactory.create().cards()
                .map(cardEntityMapper::mapToEntity)
    }

    override fun card(code: String): Observable<Card> {
        return cardsDataSourceFactory.create().card(code).map(cardEntityMapper::mapToEntity)
    }
}
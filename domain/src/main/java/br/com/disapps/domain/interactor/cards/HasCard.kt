package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

class HasCard(val cardRepository: CardsRepository) : UseCase<Boolean, HasCard.Params>(){

    override suspend fun run(params: Params): Boolean {
        return cardRepository.hasCard(params.card)
    }

    class Params (val card: Card)
}
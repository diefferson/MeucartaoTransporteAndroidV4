package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository


class UpdateCard (val cardRepository: CardsRepository) : UseCase<Unit, UpdateCard.Params>() {

    override suspend fun run(params:Params) {
        return cardRepository.updateCard(params.card)
    }

    class Params (val card: Card)
}
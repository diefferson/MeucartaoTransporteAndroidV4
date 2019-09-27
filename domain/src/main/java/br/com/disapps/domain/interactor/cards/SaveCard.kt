package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

class SaveCard (val cardRepository: CardsRepository) : UseCase<Unit, SaveCard.Params>() {

    override suspend fun run(params: SaveCard.Params) {
        return cardRepository.saveCard(params.card)
    }

    class Params (val card: Card)
}
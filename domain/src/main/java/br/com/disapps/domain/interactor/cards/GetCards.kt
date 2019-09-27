package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

class GetCards(val cardRepository: CardsRepository) : UseCase<List<Card>, UseCase.None>() {

    override suspend fun run(params: None): List<Card> {
        return cardRepository.cards()
    }
}
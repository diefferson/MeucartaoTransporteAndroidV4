package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

class DeleteCard(val cardRepository: CardsRepository) : UseCase<Unit,DeleteCard.Params>(){

    override suspend fun run(params: Params){
        return cardRepository.deleteCard(params.card)
    }

    class Params (val card: Card)
}
package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.domain.repository.CardsRepository

class GetExtract(val cardRepository: CardsRepository) : UseCase<List<Extract>?, GetExtract.Params>(){

    override suspend fun run(params: Params): List<Extract>? {
        return cardRepository.extract(params.card)
    }

    class Params (val card: Card)
}
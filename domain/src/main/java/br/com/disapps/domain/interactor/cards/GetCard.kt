package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

/**
 * Created by dnso on 15/03/2018.
 */
class GetCard(val cardRepository: CardsRepository) : UseCase<Card?, GetCard.Params>() {

    override suspend fun run(params: Params): Card? {
        return this.cardRepository.card(params.card)
    }

    class Params (val card: Card)
}
package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

/**
 * Created by dnso on 15/03/2018.
 */
class GetCard(val cardRepository: CardsRepository, val contextExecutor: ContextExecutor,
              val postExecutionContext: PostExecutionContext) : UseCase<Card?, GetCard.Params>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Params): Card? {
        return this.cardRepository.card(params.card)
    }

    class Params (val card: Card)
}
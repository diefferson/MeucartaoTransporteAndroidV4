package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.domain.repository.CardsRepository

class GetExtract(val cardRepository: CardsRepository, val contextExecutor: ContextExecutor,
                 val postExecutionContext: PostExecutionContext,
                 val logException: LogException) : UseCase<List<Extract>?, GetExtract.Params>(contextExecutor, postExecutionContext, logException){

    override suspend fun buildUseCaseObservable(params: Params): List<Extract>? {
        return cardRepository.extract(params.card)
    }

    class Params (val card: Card)
}
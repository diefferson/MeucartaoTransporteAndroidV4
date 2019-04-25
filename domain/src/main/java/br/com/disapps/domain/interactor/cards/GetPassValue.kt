package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository

/**
 * Created by dnso on 15/03/2018.
 */
class GetPassValue(val cardRepository: CardsRepository, val contextExecutor: ContextExecutor,
                   val postExecutionContext: PostExecutionContext,
                   val logException: LogException) : UseCase<Float, Unit>(contextExecutor, postExecutionContext, logException) {

    override suspend fun buildUseCaseObservable(params: Unit): Float {
        return this.cardRepository.getPassValue()
    }

}
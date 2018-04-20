package br.com.disapps.domain.interactor.events

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.model.UpdateLinesEventComplete
import br.com.disapps.domain.repository.EventsRepository
import kotlinx.coroutines.experimental.channels.ReceiveChannel

class GetUpdateLinesEvent(private val eventsRepository: EventsRepository,
                          val contextExecutor: ContextExecutor, val postExecutionContext: PostExecutionContext): BaseUseCase<ReceiveChannel<UpdateLinesEventComplete>, Unit>(contextExecutor,postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Unit): ReceiveChannel<UpdateLinesEventComplete> {
        return eventsRepository.getUpdateLinesEvent()
    }
}
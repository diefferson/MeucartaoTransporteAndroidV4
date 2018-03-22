package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.Preconditions
import br.com.disapps.domain.interactor.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Observable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread

class DeleteCard(var cardRepository: CardsRepository,
                 var threadExecutor: ThreadExecutor,
                 var postExecutionThread: PostExecutionThread)  : UseCase<Boolean, DeleteCard.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Observable<Boolean> {
        Preconditions.checkNotNull(params)
        return cardRepository.deleteCard(params.card)    }

    class Params (val card: Card) {
        companion object {
            fun forCard(card: Card): Params {
                return Params(card)
            }
        }
    }
}
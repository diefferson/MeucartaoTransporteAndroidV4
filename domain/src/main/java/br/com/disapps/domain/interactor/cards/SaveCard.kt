package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.Preconditions
import br.com.disapps.domain.interactor.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Observable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread

class SaveCard (var cardRepository: CardsRepository,
                var threadExecutor: ThreadExecutor,
                var postExecutionThread: PostExecutionThread) : UseCase<Unit, SaveCard.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: SaveCard.Params): Observable<Unit> {
        Preconditions.checkNotNull(params)
        cardRepository.saveCard(params.card)
        return Observable.just(Unit)
    }

    class Params (val card: Card) {
        companion object {
            fun forCard(card: Card): Params {
                return Params(card)
            }
        }
    }
}
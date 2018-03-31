package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.Preconditions
import br.com.disapps.domain.interactor.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Observable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread

class SaveCard (var cardRepository: CardsRepository, var threadExecutor: ThreadExecutor,
                var postExecutionThread: PostExecutionThread) : UseCase<Boolean, SaveCard.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: SaveCard.Params): Observable<Boolean> {
        Preconditions.checkNotNull(params)
        return cardRepository.saveCard(params.card)
    }

    class Params (val card: Card)
}
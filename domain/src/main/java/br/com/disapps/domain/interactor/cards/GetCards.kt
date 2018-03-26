package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Observable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread

class GetCards(var cardRepository: CardsRepository, var threadExecutor: ThreadExecutor,
                var postExecutionThread: PostExecutionThread) : UseCase<List<Card>, Unit>(threadExecutor, postExecutionThread) {


    override fun buildUseCaseObservable(params: Unit): Observable<List<Card>> {
        return cardRepository.cards()
    }


}
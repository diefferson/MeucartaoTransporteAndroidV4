package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.Preconditions
import br.com.disapps.domain.interactor.UseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Observable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread

/**
 * Created by dnso on 15/03/2018.
 */
class GetCard(var cardRepository: CardsRepository,
              var threadExecutor: ThreadExecutor,
              var postExecutionThread: PostExecutionThread) : UseCase<Card?, GetCard.Params >(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params ): Observable<Card?> {
        Preconditions.checkNotNull(params)
        return this.cardRepository.card(params.card)
    }

    class Params (val card: Card) {
        companion object {
            fun forCard(card: Card): Params {
                return Params(card)
            }
        }
    }
}
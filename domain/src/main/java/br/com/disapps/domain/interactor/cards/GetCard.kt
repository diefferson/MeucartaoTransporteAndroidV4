package br.com.disapps.domain.interactor.cards

import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import io.reactivex.Single
import br.com.disapps.domain.executor.PostExecutionThread

/**
 * Created by dnso on 15/03/2018.
 */
class GetCard(val cardRepository: CardsRepository, val threadExecutor: ThreadExecutor,
              val postExecutionThread: PostExecutionThread) : SingleUseCase<Card?, GetCard.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params): Single<Card?> {
        return this.cardRepository.card(params.card)
    }

    class Params (val card: Card)
}
package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.CompletableUseCase
import br.com.disapps.domain.repository.PreferencesRepository
import io.reactivex.Completable

class SaveIsFirstAccess(private val preferencesRepository: PreferencesRepository, val threadExecutor: ThreadExecutor,
                        val postExecutionThread: PostExecutionThread): CompletableUseCase<SaveIsFirstAccess.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Completable {
        return preferencesRepository.setIsFirstAccess(params.isFirst)
    }

    class Params(val isFirst : Boolean)
}
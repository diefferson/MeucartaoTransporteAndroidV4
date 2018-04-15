package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.repository.PreferencesRepository
import io.reactivex.Single

class GetInitialScreen(private val preferencesRepository: PreferencesRepository, val threadExecutor: ThreadExecutor,
                       val postExecutionThread: PostExecutionThread) : SingleUseCase<String, Unit>(threadExecutor, postExecutionThread){


    override fun buildUseCaseObservable(params: Unit): Single<String> {
        return preferencesRepository.getInitialScreen()
    }
}
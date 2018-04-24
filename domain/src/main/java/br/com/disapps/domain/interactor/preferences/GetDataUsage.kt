package br.com.disapps.domain.interactor.preferences

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.DataUsage
import br.com.disapps.domain.repository.PreferencesRepository

class GetDataUsage(private val preferencesRepository: PreferencesRepository, val contextExecutor: ContextExecutor,
                   val postExecutionContext: PostExecutionContext) : UseCase<DataUsage, Unit>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Unit):DataUsage {
        return preferencesRepository.getDataUsage()
    }
}
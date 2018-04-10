package br.com.disapps.domain.interactor.shapes

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.CompletableUseCase
import br.com.disapps.domain.repository.ShapesRepository
import io.reactivex.Completable

class SaveAllShapesJson(val shapesRepository: ShapesRepository, val threadExecutor: ThreadExecutor,
                        val postExecutionThread: PostExecutionThread) : CompletableUseCase<SaveAllShapesJson.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Completable {
        return shapesRepository.saveAllFromJson(params.json)
    }

    class Params (val json: String)
}
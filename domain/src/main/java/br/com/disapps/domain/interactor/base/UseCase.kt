package br.com.disapps.domain.interactor.base

import kotlinx.coroutines.CoroutineScope

/**
 * Abstract class for a UseCase
 */
abstract class UseCase<Type, in Params>  where Type : Any?  {

    abstract suspend fun run(params: Params): Type

    operator fun invoke(scope: CoroutineScope, params: Params) = scope.asyncCatching {
       run(params)
    }

    @Suppress("UNCHECKED_CAST")
    operator fun invoke(scope: CoroutineScope) = scope.asyncCatching {
        run(None() as Params)
    }

    class None
}
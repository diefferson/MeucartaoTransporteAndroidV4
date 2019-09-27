package br.com.disapps.domain.interactor.base

import br.com.disapps.domain.exception.ErrorHandler
import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.exception.LogException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class ResultAsync<T> private constructor( scope: CoroutineScope, action: suspend () -> T):KoinComponent {

    var onSuccess : (T) -> Unit = {}
    var onError : (e: KnownException) -> Unit = {}
    var onStatusChange : (status: AsyncStatus) -> Unit = {}
    private val errorHandler by inject<ErrorHandler>()
    private val logException by inject<LogException>()

    companion object {
        fun <T> with( scope: CoroutineScope, action: suspend () -> T) : ResultAsync<T> {
            return  ResultAsync(scope, action)
        }
    }

    init {
        scope.launch {
            withContext(Dispatchers.Main) { onStatusChange(AsyncStatus.RUNNING) }
            try {
                val result = action()
                withContext(Dispatchers.Main){
                    onStatusChange(AsyncStatus.DONE)
                    onSuccess(result)
                }
            }catch (e:Throwable){
                withContext(Dispatchers.Main){
                    logException.logException(e)
                    onStatusChange(AsyncStatus.ERROR)
                    onError(errorHandler.handler(e))
                }
            }
        }
    }
}

fun <T> ResultAsync<T>.onFailure(action: (exception: KnownException) -> Unit): ResultAsync<T> {
    this.onError =  action
    return this
}

fun <T> ResultAsync<T>.onSuccess(action: (value: T) -> Unit): ResultAsync<T> {
    this.onSuccess = action
    return this
}

fun <T> ResultAsync<T>.onStatusChange(action: (AsyncStatus) -> Unit): ResultAsync<T> {
    this.onStatusChange = action
    return this
}

fun <T> CoroutineScope.asyncCatching( action: suspend () -> T): ResultAsync<T> {
    return ResultAsync.with( this,action)
}


enum class AsyncStatus{
    RUNNING,
    DONE,
    ERROR
}




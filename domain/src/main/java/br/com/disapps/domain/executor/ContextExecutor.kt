package br.com.disapps.domain.executor

import kotlin.coroutines.experimental.CoroutineContext

interface ContextExecutor {
    val scheduler: CoroutineContext
}
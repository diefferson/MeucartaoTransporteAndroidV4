package br.com.disapps.domain.executor

import kotlin.coroutines.experimental.CoroutineContext

interface PostExecutionContext{
    val scheduler: CoroutineContext
}
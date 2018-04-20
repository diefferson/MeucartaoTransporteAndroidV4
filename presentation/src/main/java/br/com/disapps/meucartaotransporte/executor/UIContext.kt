package br.com.disapps.meucartaotransporte.executor

import br.com.disapps.domain.executor.PostExecutionContext
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

class UIContext : PostExecutionContext{
    override val scheduler: CoroutineContext
        get() = UI
}
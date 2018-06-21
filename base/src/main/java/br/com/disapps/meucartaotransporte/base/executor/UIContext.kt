package br.com.disapps.meucartaotransporte.base.executor

import br.com.disapps.domain.executor.PostExecutionContext
import kotlinx.coroutines.experimental.android.UI

class UIContext : PostExecutionContext{
    override val scheduler = UI
}
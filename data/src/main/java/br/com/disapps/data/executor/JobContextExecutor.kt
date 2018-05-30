package br.com.disapps.data.executor

import br.com.disapps.domain.executor.ContextExecutor
import kotlinx.coroutines.experimental.CommonPool

class JobContextExecutor : ContextExecutor{
    override val scheduler = CommonPool
}
package br.com.disapps.meucartaotransporte.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import br.com.disapps.domain.executor.PostExecutionThread

/**
 * Created by dnso on 12/03/2018.
 */

class UIThread : PostExecutionThread {

   override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}

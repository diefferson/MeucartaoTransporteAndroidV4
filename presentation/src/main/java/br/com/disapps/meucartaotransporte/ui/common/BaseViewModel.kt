package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.disapps.meucartaotransporte.exception.UiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    var isRequested  = false
    protected val exceptionEvent: MutableLiveData<UiException> = MutableLiveData()
    protected val loadingEvent: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Expose the LiveData so the UI can observe it.
     */
    fun getIsLoadingObservable(): LiveData<Boolean> {
        return loadingEvent
    }

    fun getErrorObservable(): LiveData<UiException> {
        return exceptionEvent
    }

    private val executionJob: Job by lazy { Job() }

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Default + executionJob
    }

    override fun onCleared() {
        super.onCleared()
        executionJob.cancel()
    }
}
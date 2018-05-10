package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.meucartaotransporte.util.SingleLiveEvent

open class BaseViewModel : ViewModel(){

    var isRequested  = false
    protected val errorEvent: SingleLiveEvent<KnownError> = SingleLiveEvent()
    protected val loadingEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    /**
     * Expose the LiveData so the UI can observe it.
     */
    fun getIsLoadingObservable(): LiveData<Boolean> {
        return loadingEvent
    }

    fun getErrorObservable(): LiveData<KnownError> {
        return errorEvent
    }
}
package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import br.com.disapps.meucartaotransporte.exception.UiException

open class BaseViewModel : ViewModel(){

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
}
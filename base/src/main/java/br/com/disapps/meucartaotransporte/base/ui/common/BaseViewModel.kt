package br.com.disapps.meucartaotransporte.base.ui.common

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.disapps.meucartaotransporte.base.exception.UiException

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
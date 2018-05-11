package br.com.disapps.meucartaotransporte.ui.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.meucartaotransporte.exception.UiError
import br.com.disapps.meucartaotransporte.util.SingleLiveEvent

open class BaseViewModel : ViewModel(){

    var isRequested  = false
    protected val errorEvent: MutableLiveData<UiError> = MutableLiveData()
    protected val loadingEvent: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Expose the LiveData so the UI can observe it.
     */
    fun getIsLoadingObservable(): LiveData<Boolean> {
        return loadingEvent
    }

    fun getErrorObservable(): LiveData<UiError> {
        return errorEvent
    }
}
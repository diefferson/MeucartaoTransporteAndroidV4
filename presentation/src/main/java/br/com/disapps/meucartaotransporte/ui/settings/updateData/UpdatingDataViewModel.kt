package br.com.disapps.meucartaotransporte.ui.settings.updateData

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultObserver
import br.com.disapps.domain.interactor.events.GetUpdateDataEvent
import br.com.disapps.domain.model.UpdateDataEvent
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class UpdatingDataViewModel(private val getUpdateDataEventUseCase: GetUpdateDataEvent) : BaseViewModel(){

    val events = MutableLiveData<UpdateDataEvent>()

    fun getUpdateDataEvent(){
        if(!isRequested){
            isRequested = true
            getUpdateDataEventUseCase.execute(object : DefaultObserver<UpdateDataEvent>(){
                override fun onNext(t: UpdateDataEvent) {
                    events.value = t
                }
            }, Unit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        getUpdateDataEventUseCase.dispose()
    }
}
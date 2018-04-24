package br.com.disapps.meucartaotransporte.ui.main

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.UseCaseCallback
import br.com.disapps.domain.interactor.preferences.GetInitialScreen
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 09/03/2018.
 */
class MainViewModel(private val getInitialScreenUseCase: GetInitialScreen) : BaseViewModel(){
    val searchText = MutableLiveData<String>()
    val onSearchAction = MutableLiveData<Boolean>()
    var isTabsVisible = true
    val initialScreen  = MutableLiveData<Int>()

    fun getInitialScreen(){
        initialScreen.value = 0
        getInitialScreenUseCase.execute(object : UseCaseCallback<String>(){
            override fun onSuccess(t: String) {
                initialScreen.value = if(InitialScreen.CARDS.toString() == t){
                    0
                }else{
                    1
                }
            }
        }, Unit)
    }

    override fun onCleared() {
        super.onCleared()
        getInitialScreenUseCase.dispose()
    }
}
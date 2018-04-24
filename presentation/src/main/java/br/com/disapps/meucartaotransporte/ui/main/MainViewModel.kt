package br.com.disapps.meucartaotransporte.ui.main

import android.arch.lifecycle.MutableLiveData
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
        getInitialScreenUseCase.execute(Unit){
            initialScreen.value = if(InitialScreen.CARDS.toString() == it){
                0
            }else{
                1
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getInitialScreenUseCase.dispose()
    }
}
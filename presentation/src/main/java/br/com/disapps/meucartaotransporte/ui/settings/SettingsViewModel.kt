package br.com.disapps.meucartaotransporte.ui.settings

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.UseCaseCompletableCallback
import br.com.disapps.domain.interactor.base.UseCaseCallback
import br.com.disapps.domain.interactor.preferences.GetInitialScreen
import br.com.disapps.domain.interactor.preferences.SaveInitialScreen
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class SettingsViewModel(private var getInitialScreenUseCase: GetInitialScreen,
                        private var saveInitialScreenUseCase: SaveInitialScreen) : BaseViewModel(){

    val initialScreen = MutableLiveData<String>()

    fun getInitialScreen(){
        getInitialScreenUseCase.execute(object : UseCaseCallback<String>(){
            override fun onSuccess(t: String) {
                initialScreen.value = t
            }
        }, Unit)
    }

    fun saveInitialScreen(initialScreen: InitialScreen){
        saveInitialScreenUseCase.execute(object : UseCaseCompletableCallback(){
            override fun onComplete() {
                super.onComplete()
                getInitialScreen()
            }
        }, SaveInitialScreen.Params(initialScreen))
    }

    override fun onCleared() {
        super.onCleared()
        getInitialScreenUseCase.dispose()
        saveInitialScreenUseCase.dispose()
    }
}

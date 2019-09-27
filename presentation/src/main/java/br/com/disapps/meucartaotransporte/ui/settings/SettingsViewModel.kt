package br.com.disapps.meucartaotransporte.ui.settings

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.onSuccess
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
        getInitialScreenUseCase(this).onSuccess{
            initialScreen.value = it
        }

    }

    fun saveInitialScreen(initialScreen: InitialScreen){
        saveInitialScreenUseCase(this,SaveInitialScreen.Params(initialScreen)).onSuccess{
            getInitialScreen()
        }
    }
}

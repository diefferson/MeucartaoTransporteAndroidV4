package br.com.disapps.meucartaotransporte.ui.settings

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.preferences.GetInitialScreen
import br.com.disapps.domain.interactor.preferences.SaveInitialScreen
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.meucartaotransporte.base.ui.common.BaseViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class SettingsViewModel(private var getInitialScreenUseCase: GetInitialScreen,
                        private var saveInitialScreenUseCase: SaveInitialScreen) : BaseViewModel(){

    val initialScreen = MutableLiveData<String>()

    fun loadInitialScreen(){
        getInitialScreenUseCase.execute(Unit){
            initialScreen.value = it
        }

    }

    fun saveInitialScreen(initialScreen: InitialScreen){
        saveInitialScreenUseCase.execute(SaveInitialScreen.Params(initialScreen)){
            loadInitialScreen()
        }
    }

    override fun onCleared() {
        super.onCleared()
        getInitialScreenUseCase.dispose()
        saveInitialScreenUseCase.dispose()
    }

}

package br.com.disapps.meucartaotransporte.ui.settings

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.preferences.GetInitialScreen
import br.com.disapps.domain.interactor.preferences.SaveInitialScreen
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class SettingsViewModel(private val getInitialScreenUseCase: GetInitialScreen,
                        private val saveInitialScreenUseCase: SaveInitialScreen) : BaseViewModel(){


    val initialScreen = MutableLiveData<String>()

    fun getInitialSreen(){
        getInitialScreenUseCase.execute(object : DefaultSingleObserver<String>(){
            override fun onSuccess(t: String) {
                initialScreen.value = t
            }
        }, Unit)
    }

    fun saveInitialScreen(initialScreen: InitialScreen){
        saveInitialScreenUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                super.onComplete()
                getInitialSreen()
            }
        }, SaveInitialScreen.Params(initialScreen))
    }


    override fun onCleared() {
        super.onCleared()
        getInitialScreenUseCase.dispose()
        saveInitialScreenUseCase.dispose()
    }
}

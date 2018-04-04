package br.com.disapps.meucartaotransporte.ui.main

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.meucartaotransporte.ui.cards.CardsFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 09/03/2018.
 */
class MainViewModel : BaseViewModel(){

    var actualFragment: BaseFragment = CardsFragment.newInstance()
    val searchText = MutableLiveData<String>()
    val onSearchAction = MutableLiveData<Boolean>()

}
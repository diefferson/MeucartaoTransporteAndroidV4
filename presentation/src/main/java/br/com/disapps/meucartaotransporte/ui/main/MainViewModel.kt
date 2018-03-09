package br.com.disapps.meucartaotransporte.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by dnso on 09/03/2018.
 */
class MainViewModel : ViewModel(){

    val text = MutableLiveData<String>().apply { value = "Hello World!" }

}
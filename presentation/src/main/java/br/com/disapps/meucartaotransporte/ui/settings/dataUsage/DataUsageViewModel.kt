package br.com.disapps.meucartaotransporte.ui.settings.dataUsage

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.formatDate
import java.util.*

class DataUsageViewModel(val preferences: Preferences):BaseViewModel(){

    val dateLines = MutableLiveData<String>().apply { value = "" }
    val dateSchedules  = MutableLiveData<String>().apply { value = "" }
    val dateCwbItineraries  = MutableLiveData<String>().apply { value = "" }
    val dateCwbShapes  = MutableLiveData<String>().apply { value = "" }
    val dateMetItineraries  = MutableLiveData<String>().apply { value = "" }
    val dateMetShapes  = MutableLiveData<String>().apply { value = "" }


    fun init(){
        dateLines.value = formatDate(Date(preferences.dateLines))
        dateSchedules.value = formatDate(Date(preferences.dateSchedules))

        if(preferences.dateCwbItineraries >0){
            dateCwbItineraries.value = formatDate(Date(preferences.dateCwbItineraries))
        }

        if(preferences.dateCwbShapes > 0){
            dateCwbShapes.value = formatDate(Date(preferences.dateCwbShapes))
        }

        if(preferences.dateMetItineraries > 0){
            dateMetItineraries.value = formatDate(Date(preferences.dateMetItineraries))
        }

        if(preferences.dateMetShapes > 0){
            dateMetShapes.value = formatDate(Date(preferences.dateMetShapes))
        }
    }

}
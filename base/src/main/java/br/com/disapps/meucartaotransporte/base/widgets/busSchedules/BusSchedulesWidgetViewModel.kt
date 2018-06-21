package br.com.disapps.meucartaotransporte.base.widgets.busSchedules

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import br.com.disapps.data.dataSource.local.LocalSchedulesDataSource
import br.com.disapps.data.entity.mappers.toScheduleBO
import br.com.disapps.domain.interactor.lines.GetLines
import br.com.disapps.domain.interactor.schedules.GetLineSchedules
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.domain.model.Schedule
import br.com.disapps.meucartaotransporte.base.model.LineVO
import br.com.disapps.meucartaotransporte.base.model.mappers.toLineVO
import br.com.disapps.meucartaotransporte.base.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.base.util.getDayWeek
import br.com.disapps.meucartaotransporte.base.util.validateSchedule
import br.com.disapps.meucartaotransporte.base.widgets.busSchedules.BusSchedulesPreferences.deleteLineCode
import br.com.disapps.meucartaotransporte.base.widgets.busSchedules.BusSchedulesPreferences.deleteLineColor
import br.com.disapps.meucartaotransporte.base.widgets.busSchedules.BusSchedulesPreferences.deleteLineName
import br.com.disapps.meucartaotransporte.base.widgets.busSchedules.BusSchedulesPreferences.deleteStopCode
import br.com.disapps.meucartaotransporte.base.widgets.busSchedules.BusSchedulesPreferences.deleteStopName
import br.com.disapps.meucartaotransporte.base.widgets.busSchedules.BusSchedulesPreferences.getLineCode
import br.com.disapps.meucartaotransporte.base.widgets.busSchedules.BusSchedulesPreferences.getLineColor
import br.com.disapps.meucartaotransporte.base.widgets.busSchedules.BusSchedulesPreferences.getLineName
import br.com.disapps.meucartaotransporte.base.widgets.busSchedules.BusSchedulesPreferences.getStopCode
import br.com.disapps.meucartaotransporte.base.widgets.busSchedules.BusSchedulesPreferences.getStopName
import io.realm.Realm

class BusSchedulesWidgetViewModel(private val getLinesUseCase: GetLines,
                                  private val getLineSchedulesUseCase: GetLineSchedules) : BaseViewModel(){

    val lines = MutableLiveData<List<LineVO>>()
    var lineSchedules = MutableLiveData<List<LineSchedule>>()

    fun getLines(){
        loadingEvent.value = true
        getLinesUseCase.execute(Unit, onError = {
            loadingEvent.value = false
        }) {
            loadingEvent.value = false
            lines.value = it.toLineVO()
        }
    }

    fun getLinesSchedules(codeLine :String){
        loadingEvent.value =true
        getLineSchedulesUseCase.execute(GetLineSchedules.Params(codeLine, getDayWeek()+1), onError = {
            loadingEvent.value = false
        }){
            loadingEvent.value = false
            lineSchedules.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        getLinesUseCase.dispose()
        getLineSchedulesUseCase.dispose()
    }

    companion object {

        internal fun loadSchedulesData(context: Context, appWidgetId: Int, prefsName :String, prefKey:String): BusSchedulesData {
            return BusSchedulesData(getLineCode(context, prefsName, prefKey+appWidgetId),
                    getLineName(context, prefsName, prefKey+appWidgetId),
                    getLineColor(context, prefsName, prefKey+appWidgetId),
                    getStopCode(context, prefsName, prefKey+appWidgetId),
                    getStopName(context, prefsName, prefKey+appWidgetId),
                    getNextSchedules(getLineCode(context, prefsName, prefKey + appWidgetId), getStopCode(context, prefsName, prefKey + appWidgetId)))
        }

        internal fun deleteData(context: Context, appWidgetId: Int, prefsName :String, prefKey:String){
            deleteLineCode(context, prefsName, prefKey+appWidgetId)
            deleteLineName(context, prefsName, prefKey+appWidgetId)
            deleteStopCode(context, prefsName, prefKey+appWidgetId)
            deleteStopName(context, prefsName, prefKey+appWidgetId)
            deleteLineColor(context, prefsName, prefKey+appWidgetId)
        }

        private fun getNextSchedules(codeLine: String,codeStop:String): List<Schedule> {
            val realm = Realm.getDefaultInstance()
            val schedules = realm.copyFromRealm(realm.where(LocalSchedulesDataSource.CLAZZ)
                    .equalTo(LocalSchedulesDataSource.CODE_LINE, codeLine)
                    .equalTo(LocalSchedulesDataSource.DAY, getDayWeek()+1)
                    .equalTo(LocalSchedulesDataSource.CODE_STOP, codeStop)
                    .findAll())
                    .flatMap { it.horarios }
                    .toScheduleBO()
                    .filter { validateSchedule(it,getDayWeek()+1) }.take(3)
            realm.close()

            return schedules
        }
    }
}
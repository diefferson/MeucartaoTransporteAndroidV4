package br.com.disapps.meucartaotransporte.widgets.busSchedules

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import br.com.disapps.data.dataSource.local.LocalSchedulesDataSource
import br.com.disapps.data.entity.mappers.toScheduleBO
import br.com.disapps.domain.interactor.base.onFailure
import br.com.disapps.domain.interactor.base.onSuccess
import br.com.disapps.domain.interactor.lines.GetLines
import br.com.disapps.domain.interactor.schedules.GetLineSchedules
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.domain.model.Schedule
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.model.mappers.toLineVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.getDayWeek
import br.com.disapps.meucartaotransporte.util.validateSchedule
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesPreferences.deleteLineCode
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesPreferences.deleteLineColor
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesPreferences.deleteLineName
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesPreferences.deleteStopCode
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesPreferences.deleteStopName
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesPreferences.getLineCode
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesPreferences.getLineColor
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesPreferences.getLineName
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesPreferences.getStopCode
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesPreferences.getStopName
import io.realm.Realm

class BusSchedulesWidgetViewModel(private val getLinesUseCase: GetLines,
                                  private val getLineSchedulesUseCase: GetLineSchedules) : BaseViewModel(){

    val lines = MutableLiveData<List<LineVO>>()
    var lineSchedules = MutableLiveData<List<LineSchedule>>()

    fun getLines(){
        loadingEvent.value = true
        getLinesUseCase(this).onFailure{
            loadingEvent.value = false
        }.onSuccess{
            loadingEvent.value = false
            lines.value = it.toLineVO()
        }
    }

    fun getLinesSchedules(codeLine :String){
        loadingEvent.value =true
        getLineSchedulesUseCase(this, GetLineSchedules.Params(codeLine, getDayWeek()+1)).onFailure {
            loadingEvent.value = false
        }.onSuccess{
            loadingEvent.value = false
            lineSchedules.value = it
        }
    }

    companion object {

        internal fun loadSchedulesData(context: Context, appWidgetId: Int, prefsName :String, prefKey:String): BusSchedulesData {
            return BusSchedulesData(getLineCode(context, prefsName, prefKey+appWidgetId),
                    getLineName(context, prefsName, prefKey+appWidgetId),
                    getLineColor(context, prefsName, prefKey+appWidgetId),
                    getStopCode(context, prefsName, prefKey+appWidgetId),
                    getStopName(context, prefsName, prefKey+appWidgetId),
                    getNextSchedules(getLineCode(context, prefsName, prefKey+appWidgetId), getStopCode(context, prefsName, prefKey+appWidgetId)))
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
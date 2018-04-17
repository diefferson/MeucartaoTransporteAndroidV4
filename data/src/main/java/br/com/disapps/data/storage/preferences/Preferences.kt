package br.com.disapps.data.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import br.com.disapps.data.BuildConfig
import br.com.disapps.domain.model.InitialScreen
import java.util.*

/**
 * Created by dnso on 13/03/2018.
 */
class Preferences(var context:Context) {

    companion object {
        const val PRO_ACCESS = "acessoPro"
        const val INITIAL_SCREEN = "telaInicial"
        const val FIRST_ACCESS = "first"
        const val METROPOLITAN_SHAPES = "shapesMetropolitana"
        const val CURITIBA_SHAPES = "shapesCuritiba"
        const val LINES_PERIOD = "periodoLinhas"
        const val LINES_DATE = "dataLinhas"
        const val SCHEDULERS_PERIOD = "periodoHorarios"
        const val SCHEDULERS_DATE = "dataHorarios"
        const val DATE_CURITIBA_ITINERARIES= "dataPontosCuritiba"
        const val DATE_CURITIBA_SHAPES= "dataShapesCuritiba"
        const val DATE_METROPOLITAN_ITINERARIES= "dataPontosMetropolitana"
        const val DATE_METROPOLITAN_SHAPES= "dataShapesMetropolitana"
        const val HAS_SERVICE_WORK = "serviceWork"
        const val IS_MANUAL_SERVICE = "manual"
    }

    private val mPreferences: SharedPreferences = context.getSharedPreferences(BuildConfig.PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

    val isPro: Boolean
        get() = mPreferences.getBoolean(PRO_ACCESS, false)

    val isManualService: Boolean
        get() = mPreferences.getBoolean(IS_MANUAL_SERVICE, false)

    val isFirstAccess: Int
        get() = mPreferences.getInt(FIRST_ACCESS, 0)

    val hasServiceWork: Int
        get() = mPreferences.getInt(HAS_SERVICE_WORK, 0)

    val initialScreen : String
        get() = mPreferences.getString(INITIAL_SCREEN, InitialScreen.CARDS.toString())

    val isMetropolianShapes : Boolean
        get() = mPreferences.getBoolean(METROPOLITAN_SHAPES, false)

    val isCuritibaShapes : Boolean
        get() = mPreferences.getBoolean(CURITIBA_SHAPES, false)

    val dateLines: Long
        get() = mPreferences.getLong(LINES_DATE, Calendar.getInstance().timeInMillis)

    val dateSchedules: Long
        get() = mPreferences.getLong(SCHEDULERS_DATE, Calendar.getInstance().timeInMillis)

    val dateCwbItineraries: Long
        get() = mPreferences.getLong(DATE_CURITIBA_ITINERARIES, 0)

    val dateCwbShapes: Long
        get() = mPreferences.getLong(DATE_CURITIBA_SHAPES,0)

    val dateMetItineraries: Long
        get() = mPreferences.getLong(DATE_METROPOLITAN_ITINERARIES, 0)

    val dateMetShapes: Long
        get() = mPreferences.getLong(DATE_METROPOLITAN_SHAPES, 0)

    fun setIsPro(isPro :Boolean){
        mPreferences.edit().putBoolean(PRO_ACCESS, isPro).apply()
    }

    fun setIsManualService(isManualService :Boolean){
        mPreferences.edit().putBoolean(IS_MANUAL_SERVICE, isManualService).apply()
    }

    fun setInitialScreen(screen : InitialScreen){
        mPreferences.edit().putString(INITIAL_SCREEN, screen.toString()).apply()
    }

    fun setHasServiceWork(hasServiceWork: Int){
        mPreferences.edit().putInt(HAS_SERVICE_WORK, hasServiceWork).apply()
    }

    fun setIsFirstAccess(firstAccess: Int){
        mPreferences.edit().putInt(FIRST_ACCESS, firstAccess).apply()
    }

    fun setIsMetropolianShapes(isMetropolianShapes :Boolean){
        mPreferences.edit().putBoolean(METROPOLITAN_SHAPES, isMetropolianShapes).apply()
    }

    fun setIsCuritibaShapes(isCuritibaShapes :Boolean){
        mPreferences.edit().putBoolean(CURITIBA_SHAPES, isCuritibaShapes).apply()
    }

    fun setLinesDate(){
        mPreferences.edit().putLong(LINES_DATE,Calendar.getInstance().timeInMillis ).apply()
    }

    fun setSchedulesDate(){
        mPreferences.edit().putLong(SCHEDULERS_DATE,Calendar.getInstance().timeInMillis ).apply()
    }

    fun setCwbShapesDate(){
        mPreferences.edit().putLong(DATE_CURITIBA_SHAPES,Calendar.getInstance().timeInMillis ).apply()
    }

    fun setCwbItinerariesDate(){
        mPreferences.edit().putLong(DATE_CURITIBA_ITINERARIES,Calendar.getInstance().timeInMillis ).apply()
    }

    fun setMetShapesDate(){
        mPreferences.edit().putLong(DATE_METROPOLITAN_SHAPES,Calendar.getInstance().timeInMillis ).apply()
    }

    fun setMetItinerariesDate(){
        mPreferences.edit().putLong(DATE_METROPOLITAN_ITINERARIES,Calendar.getInstance().timeInMillis ).apply()
    }

}
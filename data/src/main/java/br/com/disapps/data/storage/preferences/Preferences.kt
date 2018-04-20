package br.com.disapps.data.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import br.com.disapps.data.BuildConfig
import br.com.disapps.domain.model.DataUsage
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.domain.model.PeriodUpdate
import br.com.disapps.domain.repository.PreferencesRepository
import java.util.*

/**
 * Created by dnso on 13/03/2018.
 */
class Preferences(var context:Context) : PreferencesRepository {

    private val mPreferences: SharedPreferences = context.getSharedPreferences(BuildConfig.PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

    override suspend fun getIsPro(): Boolean {
        return mPreferences.getBoolean(PRO_ACCESS, false)
    }

    override suspend fun getInitialScreen(): String {
        return mPreferences.getString(INITIAL_SCREEN, InitialScreen.CARDS.toString())
    }

    override suspend fun getIsFirstAccess(): Boolean {
        return mPreferences.getInt(FIRST_ACCESS, 0)==0
    }

    private fun getDateLines(): Long {
        return mPreferences.getLong(LINES_DATE, Calendar.getInstance().timeInMillis)
    }

    private fun getDateSchedules(): Long {
        return mPreferences.getLong(SCHEDULERS_DATE, Calendar.getInstance().timeInMillis)
    }

    private fun getDateCwbItineraries(): Long {
        return mPreferences.getLong(DATE_CURITIBA_ITINERARIES, 0)
    }

    private fun getDateMetItineraries(): Long {
        return mPreferences.getLong(DATE_METROPOLITAN_ITINERARIES, 0)
    }

    private fun getDateCwbShapes(): Long {
        return mPreferences.getLong(DATE_CURITIBA_SHAPES,0)
    }

    private fun getDateMetShapes(): Long {
        return mPreferences.getLong(DATE_METROPOLITAN_SHAPES, 0)
    }

    private fun getPeriodLines() :String{
        return  mPreferences.getString(LINES_PERIOD, "mensal")
    }

    private fun getPeriodSchedules() :String{
        return  mPreferences.getString(SCHEDULERS_PERIOD, "semanal")
    }

    override suspend fun getDataUsage(): DataUsage {
        return DataUsage(
                periodLines = getPeriodLines(),
                periodSchedules = getPeriodSchedules(),
                dateUpdateLines  = getDateLines(),
                dateUpdateSchedules  = getDateSchedules(),
                dateUpdateCwbItineraries = getDateCwbItineraries(),
                dateUpdateMetItineraries = getDateMetItineraries(),
                dateUpdateCwbShapes = getDateCwbShapes(),
                dateUpdateMetShapes = getDateMetShapes()
        )
    }

    override suspend fun setIsPro(isPro :Boolean){
        mPreferences.edit().putBoolean(PRO_ACCESS, isPro).apply()
    }

    override suspend fun setIsFirstAccess(isFirstAccess: Boolean) {

        var firstAccess= 0

        if(!isFirstAccess){
            firstAccess = 1
        }

        mPreferences.edit().putInt(FIRST_ACCESS, firstAccess).apply()
    }

    override suspend fun setInitialScreen(initialScreen : InitialScreen){
        mPreferences.edit().putString(INITIAL_SCREEN, initialScreen.toString()).apply()
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

    override suspend fun setPeriodUpdateLines(period: PeriodUpdate) {
        mPreferences.edit().putString(LINES_PERIOD, period.toString()).apply()
    }

    override suspend fun setPeriodUpdateSchedules(period: PeriodUpdate) {
        mPreferences.edit().putString(SCHEDULERS_PERIOD, period.toString()).apply()
    }

    companion object {
        const val PRO_ACCESS = "acessoPro"
        const val INITIAL_SCREEN = "telaInicial"
        const val FIRST_ACCESS = "first"
        const val LINES_PERIOD = "periodoLinhas"
        const val LINES_DATE = "dataLinhas"
        const val SCHEDULERS_PERIOD = "periodoHorarios"
        const val SCHEDULERS_DATE = "dataHorarios"
        const val DATE_CURITIBA_ITINERARIES= "dataPontosCuritiba"
        const val DATE_CURITIBA_SHAPES= "dataShapesCuritiba"
        const val DATE_METROPOLITAN_ITINERARIES= "dataPontosMetropolitana"
        const val DATE_METROPOLITAN_SHAPES= "dataShapesMetropolitana"
    }
}
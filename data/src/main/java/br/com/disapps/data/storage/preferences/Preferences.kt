package br.com.disapps.data.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract
import br.com.disapps.data.BuildConfig
import br.com.disapps.domain.model.DataUsage
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.domain.repository.PreferencesRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

/**
 * Created by dnso on 13/03/2018.
 */
class Preferences(var context:Context) : PreferencesRepository {

    private val mPreferences: SharedPreferences = context.getSharedPreferences(BuildConfig.PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

    override fun getIsPro(): Single<Boolean> {
        return Single.just(mPreferences.getBoolean(PRO_ACCESS, false))
    }

    override fun getInitialScreen(): Single<String> {
        return Single.just(mPreferences.getString(INITIAL_SCREEN, InitialScreen.CARDS.toString()))
    }

    override fun getIsFirstAccess(): Single<Boolean> {
        return Single.just(mPreferences.getInt(FIRST_ACCESS, 0)==0)
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

    override fun getDataUsage(): Single<DataUsage> {
        return Single.just(DataUsage(
                periodLines = getPeriodLines(),
                periodSchedules = getPeriodSchedules(),
                dateUpdateLines  = getDateLines(),
                dateUpdateSchedules  = getDateSchedules(),
                dateUpdateCwbItineraries = getDateCwbItineraries(),
                dateUpdateMetItineraries = getDateMetItineraries(),
                dateUpdateCwbShapes = getDateCwbShapes(),
                dateUpdateMetShapes = getDateMetShapes()
        ))
    }

    override fun setIsPro(isPro :Boolean) : Completable{
        return Completable.defer {
            mPreferences.edit().putBoolean(PRO_ACCESS, isPro).apply()
            Completable.complete()
        }
    }

    override fun setIsFirstAccess(isFirstAccess: Boolean): Completable {

        var firstAccess= 0

        if(!isFirstAccess){
            firstAccess = 1
        }

        return Completable.defer {
            mPreferences.edit().putInt(FIRST_ACCESS, firstAccess).apply()
            Completable.complete()
        }
    }

    override fun setInitialScreen(initialScreen : InitialScreen): Completable{
        return Completable.defer {
            mPreferences.edit().putString(INITIAL_SCREEN, initialScreen.toString()).apply()
            Completable.complete()
        }
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
}
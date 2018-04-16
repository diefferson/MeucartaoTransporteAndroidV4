package br.com.disapps.data.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import br.com.disapps.data.BuildConfig
import br.com.disapps.domain.model.InitialScreen

/**
 * Created by dnso on 13/03/2018.
 */
class Preferences(var context:Context) {

    companion object {
        val PRO_ACCESS = "acessoPro"
        val INITIAL_SCREEN = "telaInicial"
        val FIRST_ACCESS = "first"
        val METROPOLITAN_SHAPES = "shapesMetropolitana"
        val CURITIBA_SHAPES = "shapesCuritiba"
        val LINES_PERIOD = "periodoLinhas"
        val LINES_DATE = "dataLinhas"
        val SCHEDULERS_PERIOD = "periodoHorarios"
        val SCHEDULERS_DATE = "dataHorarios"
        val DATE_CURITIBA_POINTS= "dataPontosCuritiba"
        val DATE_CURITIBA_SHAPES= "dataShapesCuritiba"
        val DATE_METROPOLITAN_POINTS= "dataPontosMetropolitana"
        val DATE_METROPOLITAN_SHAPES= "dataShapesMetropolitana"
    }

    private val mPreferences: SharedPreferences = context.getSharedPreferences(BuildConfig.PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

    val isPro: Boolean
        get() = mPreferences.getBoolean(PRO_ACCESS, false)

    val isFirstAccess: Int
        get() = mPreferences.getInt(FIRST_ACCESS, 0)

    val initialScreen : String
        get() = mPreferences.getString(INITIAL_SCREEN, InitialScreen.CARDS.toString())

    val isMetropolianShapes : Boolean
        get() = mPreferences.getBoolean(METROPOLITAN_SHAPES, false)

    val isCuritibaShapes : Boolean
        get() = mPreferences.getBoolean(CURITIBA_SHAPES, false)


    fun setIsPro(isPro :Boolean){
        mPreferences.edit().putBoolean(PRO_ACCESS, isPro).apply()
    }

    fun setInitialScreen(screen : InitialScreen){
        mPreferences.edit().putString(INITIAL_SCREEN, screen.toString()).apply()
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

}
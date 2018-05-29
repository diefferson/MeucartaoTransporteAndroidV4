package br.com.disapps.domain.repository

import br.com.disapps.domain.model.DataUsage
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.domain.model.PeriodUpdate

interface PreferencesRepository{
    suspend fun getIsPro() : Boolean
    suspend fun getInitialScreen() : String
    suspend fun getIsFirstAccess() : Boolean
    suspend fun getDataUsage(): DataUsage
    suspend fun getIsDownloadedCwbItineraries() : Boolean
    suspend fun getIsDownloadedMetropolitanItineraries() : Boolean
    suspend fun getIsDownloadedCwbShapes() : Boolean
    suspend fun getIsDownloadedMetropolitanShapes() : Boolean

    suspend fun setIsPro(isPro: Boolean)
    suspend fun setInitialScreen(initialScreen: InitialScreen)
    suspend fun setIsFirstAccess(isFirstAccess: Boolean)
    suspend fun setPeriodUpdateLines(period: PeriodUpdate)
    suspend fun setPeriodUpdateSchedules(period: PeriodUpdate)
}
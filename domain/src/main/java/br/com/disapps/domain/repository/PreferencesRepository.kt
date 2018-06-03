package br.com.disapps.domain.repository

import br.com.disapps.domain.model.DataUsage
import br.com.disapps.domain.model.InitialScreen

interface PreferencesRepository{
    suspend fun getIsPro() : Boolean
    suspend fun getInitialScreen() : String
    fun getIsFirstAccess() : Boolean
    suspend fun getDataUsage(): DataUsage
    suspend fun getIsDownloadedCwbItineraries() : Boolean
    suspend fun getIsDownloadedMetropolitanItineraries() : Boolean
    suspend fun getIsDownloadedCwbShapes() : Boolean
    suspend fun getIsDownloadedMetropolitanShapes() : Boolean
    fun getIdDownloadLines():Long
    fun getIdDownloadSchedules():Long
    fun getIdDownloadItinerariesCwb():Long
    fun getIdDownloadShapesCwb():Long
    fun getIdDownloadItinerariesMetropolitan():Long
    fun getIdDownloadShapesMetropolitan():Long

    suspend fun setIsPro(isPro: Boolean)
    suspend fun setInitialScreen(initialScreen: InitialScreen)
    suspend fun setIsFirstAccess(isFirstAccess: Boolean)
    fun setIdDownloadLines(id: Long)
    fun setIdDownloadSchedules(id: Long)
    fun setIdDownloadItinerariesCwb(id: Long)
    fun setIdDownloadShapesCwb(id: Long)
    fun setIdDownloadItinerariesMetropolitan(id: Long)
    fun setIdDownloadShapesMetropolitan(id: Long)
}
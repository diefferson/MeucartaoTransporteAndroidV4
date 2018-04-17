package br.com.disapps.domain.repository

import br.com.disapps.domain.model.InitialScreen
import io.reactivex.Completable
import io.reactivex.Single

interface PreferencesRepository{

    fun getInitialScreen() : Single<String>
    fun saveInitialScreen(initialScreen: InitialScreen) : Completable
    fun getIsFirstAccess() : Single<Boolean>
    fun setIsFirstAccess(isFirst : Boolean) : Completable
    fun getDateLines() : Single<Long>
    fun getDateSchedules() : Single<Long>
    fun getDateCwbItineraries(): Single<Long>
    fun getDateMetItineraries(): Single<Long>
    fun getDateCwbShapes(): Single<Long>
    fun getDateMetShapes(): Single<Long>
}
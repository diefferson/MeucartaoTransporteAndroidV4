package br.com.disapps.domain.repository

import br.com.disapps.domain.model.DataUsage
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.domain.model.PeriodUpdate
import io.reactivex.Completable
import io.reactivex.Single

interface PreferencesRepository{
    fun getIsPro() : Single<Boolean>
    fun getInitialScreen() : Single<String>
    fun getIsFirstAccess() : Single<Boolean>
    fun getDataUsage(): Single<DataUsage>

    fun setIsPro(isPro: Boolean) : Completable
    fun setInitialScreen(initialScreen: InitialScreen) : Completable
    fun setIsFirstAccess(isFirstAccess: Boolean) : Completable
    fun setPeriodUpdateLines(period: PeriodUpdate) : Completable
    fun setPeriodUpdateSchedules(period: PeriodUpdate) : Completable
}
package br.com.disapps.data.repository

import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.domain.repository.PreferencesRepository
import io.reactivex.Completable
import io.reactivex.Single


class PreferencesDataRepository(private val preferences:Preferences) : PreferencesRepository{

    override fun getInitialScreen(): Single<String> {
        return Single.just(preferences.initialScreen)
    }

    override fun saveInitialScreen(initialScreen: InitialScreen): Completable {
        return  Completable.defer {
            preferences.setInitialScreen(initialScreen)
            Completable.complete()
        }
    }

    override fun getIsFirstAccess(): Single<Boolean> {
        return Single.just(  preferences.isFirstAccess==0)
    }

    override fun setIsFirstAccess(isFirst: Boolean): Completable {

        var firstAccess= 0

        if(!isFirst){
            firstAccess = 1
        }

        return Completable.defer {
            preferences.setIsFirstAccess(firstAccess)
            Completable.complete()
        }
    }

    override fun getDateLines(): Single<Long> {
        return Single.just(preferences.dateLines)
    }

    override fun getDateSchedules(): Single<Long> {
        return Single.just(preferences.dateSchedules)
    }

    override fun getDateCwbItineraries(): Single<Long> {
        return Single.just(preferences.dateCwbItineraries)
    }

    override fun getDateMetItineraries(): Single<Long> {
        return Single.just(preferences.dateMetItineraries)
    }

    override fun getDateCwbShapes(): Single<Long> {
        return Single.just(preferences.dateCwbShapes)
    }

    override fun getDateMetShapes(): Single<Long> {
        return Single.just(preferences.dateMetShapes)
    }
}
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
}
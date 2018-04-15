package br.com.disapps.domain.repository

import br.com.disapps.domain.model.InitialScreen
import io.reactivex.Completable
import io.reactivex.Single

interface PreferencesRepository{

    fun getInitialScreen() : Single<String>
    fun saveInitialScreen(initialScreen: InitialScreen) : Completable
}
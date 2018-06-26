package br.com.disapps.meucartaotransporte.app

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin
import org.koin.log.EmptyLogger

/**
 * Created by dnso on 08/03/2018.
 */

class App : Application() {

    private val database : Database by inject()
    val preferences :Preferences  by inject()

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        instance = this

        startKoin(this, AppInject.modules(), logger = EmptyLogger())

        database.initDatabase()
    }

    companion object {
        var instance: App? = null
            private set
    }
}
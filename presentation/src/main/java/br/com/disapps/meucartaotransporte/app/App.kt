package br.com.disapps.meucartaotransporte.app

import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences
import com.onesignal.OneSignal
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by dnso on 08/03/2018.
 */

class App : MultiDexApplication() {

    private val database : Database by inject()
    val preferences :Preferences  by inject()

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        instance = this

        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@App)
            modules(AppInject.modules())
        }

        database.initDatabase()

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init()

    }

    companion object {
        var instance: App? = null
            private set
    }
}
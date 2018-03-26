package br.com.disapps.data.storage.database

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by diefferson on 10/03/2018.
 */
class RealmDatabase(var context: Context) : Database{

    override fun initDatabase() {
        Realm.init(context)

        val realmConfiguration = RealmConfiguration.Builder()
                .name("mct.realm")
                .schemaVersion(MigrationData.VERSION)
                .migration(MigrationData())
                .build()

        Realm.setDefaultConfiguration(realmConfiguration)

    }

    override fun getDatabase(): Any {
        return Realm.getDefaultInstance()
    }

}
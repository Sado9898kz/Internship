package com.example.bbcnews

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created work Sado on 24.05.2019
 */
class App:Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val realmConfig=RealmConfiguration.Builder()
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfig)

    }
}
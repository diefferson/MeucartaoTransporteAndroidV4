package br.com.disapps.data.dataSource.factory

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.ItinerariesDataSource
import br.com.disapps.data.dataSource.cloud.CloudItinerariesDataSource
import br.com.disapps.data.dataSource.local.LocalItinerariesDataSource
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences

class ItinerariesDataSourceFactory(private val database: Database, private val restApi: RestApi,private val preferences: Preferences) : DataSourceFactory {

    override fun create(useCloud: Boolean): ItinerariesDataSource {
        return if(useCloud){
            CloudItinerariesDataSource(restApi)
        }else{
            LocalItinerariesDataSource(database, preferences)
        }
    }
}
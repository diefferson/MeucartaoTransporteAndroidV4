package br.com.disapps.data.dataSource.factory

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.SchedulesDataSource
import br.com.disapps.data.dataSource.cloud.CloudSchedulesDataSource
import br.com.disapps.data.dataSource.local.LocalSchedulesDataSource
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.storage.preferences.Preferences

class SchedulesDataSourceFactory(private val database: Database, private val restApi: RestApi, private val preferences: Preferences) : DataSourceFactory  {

    override fun create(useCloud: Boolean): SchedulesDataSource {
        return if(useCloud){
            CloudSchedulesDataSource(restApi)
        }else{
            LocalSchedulesDataSource(database, preferences)
        }
    }
}
package br.com.disapps.data.dataSource.factory

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.SchedulesDataSource
import br.com.disapps.data.dataSource.cloud.CloudSchedulesDataSource
import br.com.disapps.data.dataSource.local.LocalSchedulesDataSource
import br.com.disapps.data.storage.database.Database

class SchedulesDataSourceFactory(private val database: Database, private val restApi: RestApi) : DataSourceFactory  {

    override fun create(useCloud: Boolean): SchedulesDataSource {
        return if(useCloud){
            CloudSchedulesDataSource(restApi)
        }else{
            LocalSchedulesDataSource(database)
        }
    }
}
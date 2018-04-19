package br.com.disapps.data.dataSource.factory

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.BusesDataSource
import br.com.disapps.data.dataSource.cloud.CloudBusesDataSource
import br.com.disapps.data.dataSource.local.LocalBusesDataSource
import br.com.disapps.data.storage.database.Database

class BusesDataSourceFactory(private val database: Database, private val restApi: RestApi) : DataSourceFactory{

    override fun create(useCloud: Boolean): BusesDataSource {
        return if(useCloud){
            CloudBusesDataSource(restApi)
        }else{
            LocalBusesDataSource(database)
        }
    }
}
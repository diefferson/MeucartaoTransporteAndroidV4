package br.com.disapps.data.dataSource.factory

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.ShapesDataSource
import br.com.disapps.data.dataSource.cloud.CloudShapesDataSource
import br.com.disapps.data.dataSource.local.LocalShapesDataSource
import br.com.disapps.data.storage.database.Database

class ShapesDataSourceFactory(private val database: Database, private val restApi: RestApi) : DataSourceFactory{

    override fun create(useCloud: Boolean): ShapesDataSource {
        return if(useCloud){
            CloudShapesDataSource(restApi)
        }else{
            LocalShapesDataSource(database)
        }
    }
}
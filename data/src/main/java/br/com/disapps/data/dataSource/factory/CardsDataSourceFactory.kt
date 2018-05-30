package br.com.disapps.data.dataSource.factory

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.CardsDataSource
import br.com.disapps.data.dataSource.cloud.CloudCardsDataSource
import br.com.disapps.data.dataSource.local.LocalCardsDataSource
import br.com.disapps.data.storage.database.Database

/**
 * Created by dnso on 15/03/2018.
 */
class CardsDataSourceFactory(private val database: Database, private val restApi: RestApi) : DataSourceFactory {

    override fun create(useCloud: Boolean): CardsDataSource {
        return if(useCloud){
            CloudCardsDataSource(restApi)
        }else{
            LocalCardsDataSource(database)
        }
    }
}
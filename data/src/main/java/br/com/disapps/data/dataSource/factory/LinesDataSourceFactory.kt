package br.com.disapps.data.dataSource.factory

import br.com.disapps.data.dataSource.cloud.CloudLinesDataSource
import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.LinesDataSource
import br.com.disapps.data.storage.database.Database
import br.com.disapps.data.dataSource.local.LocalLinesDataSource

/**
 * Created by dnso on 15/03/2018.
 */
class LinesDataSourceFactory(private val database: Database, private val restApi: RestApi) : DataSourceFactory {

    override fun create(useCloud: Boolean): LinesDataSource {
        return if(useCloud)
            CloudLinesDataSource(restApi)
        else
            LocalLinesDataSource(database)
    }
}
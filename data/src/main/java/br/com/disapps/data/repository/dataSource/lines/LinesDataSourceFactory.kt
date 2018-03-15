package br.com.disapps.data.repository.dataSource.lines

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.repository.dataSource.DataSourceFactory
import br.com.disapps.data.storage.database.Database

/**
 * Created by dnso on 15/03/2018.
 */
class LinesDataSourceFactory(private var database: Database, private var restApi: RestApi) : DataSourceFactory {

    override fun create(useCloud: Boolean): LinesDataSource {
        return if(useCloud)
            CloudLinesDataSource(restApi)
        else
            LocalLinesDataSource(database)
    }
}
package br.com.disapps.data.repository.dataSource.cards

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.repository.dataSource.DataSourceFactory
import br.com.disapps.data.storage.database.Database

/**
 * Created by dnso on 15/03/2018.
 */
class CardsDataSourceFactory(private var database: Database,private var restApi: RestApi) : DataSourceFactory {

    override fun create(useCloud: Boolean): CardsDataSource {
        return LocalCardsDataSource(database)
    }
}
package br.com.disapps.data.repository.dataSource

/**
 * Created by dnso on 15/03/2018.
 */
interface DataSourceFactory {

    fun create(useCloud : Boolean = false) : DataSource
}
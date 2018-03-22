package br.com.disapps.data.storage.database

/**
 * Created by diefferson on 10/03/2018.
 */
interface Database {

    fun initDatabase()
    fun getDatabase() : Any
    fun closeDataBase()

}
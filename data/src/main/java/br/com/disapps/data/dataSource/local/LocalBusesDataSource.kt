package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.BusesDataSource
import br.com.disapps.data.entity.Veiculo
import br.com.disapps.data.storage.database.Database

class LocalBusesDataSource(private val database: Database) : BusesDataSource{

    override suspend fun getAllBuses(codeLine: String): List<Veiculo> {
        throw Throwable("not implemented,  cloud only")
    }
}
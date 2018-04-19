package br.com.disapps.data.dataSource.local

import br.com.disapps.data.dataSource.BusesDataSource
import br.com.disapps.data.entity.Veiculo
import br.com.disapps.data.storage.database.Database
import io.reactivex.Observable

class LocalBusesDataSource(private val database: Database) : BusesDataSource{

    override fun getAllBuses(codeLine: String): Observable<List<Veiculo>> {
        return Observable.error(Throwable("not implemented,  cloud only"))
    }
}
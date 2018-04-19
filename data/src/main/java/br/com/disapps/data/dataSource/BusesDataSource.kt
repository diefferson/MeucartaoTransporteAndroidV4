package br.com.disapps.data.dataSource

import br.com.disapps.data.entity.Veiculo
import io.reactivex.Observable

interface BusesDataSource : DataSource{

    fun getAllBuses(codeLine : String) : Observable<List<Veiculo>>
}
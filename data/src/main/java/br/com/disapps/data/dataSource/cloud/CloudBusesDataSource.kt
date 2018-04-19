package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.BusesDataSource
import br.com.disapps.data.entity.Veiculo
import io.reactivex.Observable

class CloudBusesDataSource(private val restApi: RestApi) : BusesDataSource{

    override fun getAllBuses(codeLine: String): Observable<List<Veiculo>> {
        return restApi.listaVeiculos(codeLine)
    }
}

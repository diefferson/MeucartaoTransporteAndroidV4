package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.BusesDataSource
import br.com.disapps.data.entity.Veiculo

class CloudBusesDataSource(private val restApi: RestApi) : BusesDataSource{

    override suspend fun getAllBuses(codeLine: String): List<Veiculo> {
        return restApi.listaVeiculos(l=codeLine).await()
    }
}

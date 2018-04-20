package br.com.disapps.data.dataSource

import br.com.disapps.data.entity.Veiculo

interface BusesDataSource : DataSource{

    suspend fun getAllBuses(codeLine : String) : List<Veiculo>
}
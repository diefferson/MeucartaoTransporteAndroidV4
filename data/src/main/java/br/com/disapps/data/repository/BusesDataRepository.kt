package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.BusesDataSourceFactory
import br.com.disapps.data.entity.mappers.toBusBO
import br.com.disapps.domain.model.Bus
import br.com.disapps.domain.repository.BusesRepository

class BusesDataRepository(private val busesDataSourceFactory: BusesDataSourceFactory) : BusesRepository{

    override suspend fun getAllBuses(codeLine: String): List<Bus>{
            return busesDataSourceFactory
                    .create(true)
                    .getAllBuses(codeLine)
                    .map { it.toBusBO() }

    }
}
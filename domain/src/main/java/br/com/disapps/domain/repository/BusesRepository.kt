package br.com.disapps.domain.repository

import br.com.disapps.domain.model.Bus

interface BusesRepository{
    suspend fun getAllBuses(codeLine :String) : List<Bus>
}
package br.com.disapps.domain.repository

import br.com.disapps.domain.model.Bus
import io.reactivex.Observable

interface BusesRepository{
    fun getAllBuses(codeLine :String) : Observable<List<Bus>>
}
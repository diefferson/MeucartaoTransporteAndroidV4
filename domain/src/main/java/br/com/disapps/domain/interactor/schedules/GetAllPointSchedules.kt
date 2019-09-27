package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Schedule
import br.com.disapps.domain.repository.SchedulesRepository

class GetAllPointSchedules(val schedulesRepository: SchedulesRepository): UseCase<List<Schedule>, GetAllPointSchedules.Params>() {

    override suspend fun run(params: Params): List<Schedule> {
        return schedulesRepository.getAllPointSchedules(params.codeLine, params.day, params.codePoint)
    }

    class Params(val codeLine: String, val day: Int, val codePoint : String)
}
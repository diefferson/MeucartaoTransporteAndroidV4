package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.domain.repository.SchedulesRepository

class GetLineSchedules(val schedulesRepository: SchedulesRepository): UseCase<List<LineSchedule>, GetLineSchedules.Params>() {

    override suspend fun run(params: Params): List<LineSchedule> {
        return schedulesRepository.getLineSchedules(params.codeLine, params.day)
    }

    class Params(val codeLine: String, val day:Int)

}
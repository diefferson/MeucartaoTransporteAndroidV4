package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.SchedulesRepository

class GetLineScheduleDays (val schedulesRepository: SchedulesRepository): UseCase<List<Int>, GetLineScheduleDays.Params>() {

    override suspend fun run(params: Params): List<Int> {
        return schedulesRepository.getLineSchedulesDays(params.codeLine)
    }

    class Params(val codeLine: String)
}
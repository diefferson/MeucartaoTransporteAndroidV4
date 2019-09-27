package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.SchedulesRepository

class SaveAllSchedulesJsonOnly(val schedulesRepository: SchedulesRepository) : UseCase<Unit, SaveAllSchedulesJsonOnly.Params>(){

    override suspend fun run(params: Params) {
        return schedulesRepository.saveAllFromJson(params.filePath)
    }

    class Params (val filePath:String)
}
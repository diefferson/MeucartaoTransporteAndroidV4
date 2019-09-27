package br.com.disapps.domain.interactor.schedules

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.repository.SchedulesRepository

class SaveAllSchedulesJson(val schedulesRepository: SchedulesRepository) : UseCase<Unit, SaveAllSchedulesJson.Params>(){

    override suspend fun run(params: Params) {
        return schedulesRepository.saveAllFromJson(params.filePath, params.downloadProgressListener)
    }

    class Params (val filePath:String, val downloadProgressListener: DownloadProgressListener)
}
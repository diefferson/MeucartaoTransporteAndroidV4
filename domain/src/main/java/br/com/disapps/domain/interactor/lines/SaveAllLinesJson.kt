package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.repository.LinesRepository

class SaveAllLinesJson(val linesRepository: LinesRepository): UseCase<Unit, SaveAllLinesJson.Params>(){

    override suspend fun run(params: Params) {
        return linesRepository.saveAllLinesFromJson(params.filePath, params.downloadProgressListener)
    }

    class Params (val filePath:String, val downloadProgressListener: DownloadProgressListener)

}
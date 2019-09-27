package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository

class GetLine(val linesRepository: LinesRepository): UseCase<Line, GetLine.Params>() {

    override suspend fun run(params: Params): Line {
        return linesRepository.line(Line(params.codeLine, "", "", "",  false))
    }

    class Params(val codeLine:String)
}
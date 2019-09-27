package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository

class GetLines(val linesRepository: LinesRepository): UseCase<List<Line>, UseCase.None>() {

    override suspend fun run(params: None): List<Line> {
        return linesRepository.lines()
    }
}
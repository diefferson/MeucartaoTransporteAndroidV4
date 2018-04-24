package br.com.disapps.meucartaotransporte.ui.line

import br.com.disapps.domain.interactor.base.UseCaseCompletableCallback
import br.com.disapps.domain.interactor.lines.UpdateLine
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.model.mappers.toLineBO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class LineViewModel( private val updateLineUseCase: UpdateLine) : BaseViewModel(){
    var isTabsVisible = true
    lateinit var line: LineVO


    fun favoriteLine(){
        line.favorite = !line.favorite
        updateLineUseCase.execute(object : UseCaseCompletableCallback(){},UpdateLine.Params(line.toLineBO()))
    }

    override fun onCleared() {
        super.onCleared()
        updateLineUseCase.dispose()
    }
}
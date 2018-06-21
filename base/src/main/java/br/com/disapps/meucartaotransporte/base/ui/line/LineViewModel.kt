package br.com.disapps.meucartaotransporte.base.ui.line

import br.com.disapps.domain.interactor.lines.UpdateLine
import br.com.disapps.meucartaotransporte.base.model.LineVO
import br.com.disapps.meucartaotransporte.base.model.mappers.toLineBO
import br.com.disapps.meucartaotransporte.base.ui.common.BaseViewModel

class LineViewModel( private val updateLineUseCase: UpdateLine) : BaseViewModel(){
    var isTabsVisible = true
    var line: LineVO = LineVO("", "", "", "", false,"")


    fun favoriteLine(){

        line.favorite = !line.favorite
        updateLineUseCase.execute(UpdateLine.Params(line.toLineBO()))
    }

    override fun onCleared() {
        super.onCleared()
        updateLineUseCase.dispose()
    }
}
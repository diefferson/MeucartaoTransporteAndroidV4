package br.com.disapps.meucartaotransporte.ui.line

import android.util.Log
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.lines.UpdateLine
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.model.mappers.toLineBO
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.ui.line.nextSchedules.NextSchedulesFragment

class LineViewModel( private val updateLineUseCase: UpdateLine) : BaseViewModel(){
    var isTabsVisible = true
    lateinit var line: LineVO


    fun favoriteLine(){
        line.favorite = !line.favorite
        updateLineUseCase.execute(object : DefaultCompletableObserver(){},UpdateLine.Params(line.toLineBO()))
    }

    override fun onCleared() {
        super.onCleared()
        updateLineUseCase.dispose()
    }
}
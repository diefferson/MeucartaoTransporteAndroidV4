package br.com.disapps.meucartaotransporte.ui.lines.searchView

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.lines.GetLines
import br.com.disapps.domain.interactor.lines.UpdateLine
import br.com.disapps.domain.model.Line
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.extensions.clean


class SearchViewViewModel(private val getLinesUseCase: GetLines,
                          private val updateLineUseCase: UpdateLine) : BaseViewModel(){

    val lines = MutableLiveData<List<Line>>()
    var linesFilter : List<Line> = ArrayList()

    private var isRequested  = false

    fun filterLines(query:String){
        lines.value = linesFilter.filter {
            line -> this@SearchViewViewModel.filter(line, query)
        }
    }

     fun filter(line: Line, query: String) : Boolean{
        return line.name.toLowerCase().clean().contains(query.toLowerCase().clean())
                || line.code.toLowerCase().startsWith(query.toLowerCase())
    }





}
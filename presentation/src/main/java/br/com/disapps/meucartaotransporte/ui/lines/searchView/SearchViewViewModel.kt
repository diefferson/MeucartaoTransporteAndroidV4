package br.com.disapps.meucartaotransporte.ui.lines.searchView

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.DefaultObserver
import br.com.disapps.domain.interactor.lines.GetLines
import br.com.disapps.domain.interactor.lines.UpdateLine
import br.com.disapps.domain.model.Line
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.extensions.clean


class SearchViewViewModel(private val getLinesUseCase: GetLines,
                          private val updateLineUseCase: UpdateLine) : BaseViewModel(){

    init {
        getLines()
    }

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

    fun getLines(refresh :Boolean = false){

        if(!isRequested || refresh) {

            isRequested = true
            loadingEvent.value = true

            getLinesUseCase.execute(object : DefaultObserver<List<Line>>() {

                override fun onError(exception: Throwable) {
                    loadingEvent.value = false
                }

                override fun onNext(t: List<Line>) {
                    loadingEvent.value = false
                    linesFilter = t
                    lines.value = t
                }
            }, Unit)
        }
    }

    fun favoriteLine(line: Line){
        line.favorite = !line.favorite
        updateLineUseCase.execute(object : DefaultObserver<Boolean>(){
            override fun onNext(t: Boolean) {
                getLines(true)
            }
        }, UpdateLine.Params(line))
    }

}
package br.com.disapps.meucartaotransporte.ui.lines

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.lines.GetLines
import br.com.disapps.domain.interactor.lines.UpdateLine
import br.com.disapps.domain.model.Line
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.extensions.clean

/**
 * Created by dnso on 12/03/2018.
 */
class LinesViewModel(private val getLinesUseCase: GetLines,
                     private val updateLineUseCase: UpdateLine) : BaseViewModel(){

    private var isRequested  = false
    val lines = MutableLiveData<List<Line>>()
    val linesFiltered = ArrayList<Line>()
    val favoriteLines = MutableLiveData<List<Line>>()
    val hasFavorite = MutableLiveData<Boolean>()

    fun filterLines(query:String){
        linesFiltered.clear()
        lines.value?.forEach {
            if(filter(it, query)){
                linesFiltered.add(it)
            }
        }
    }

    private fun filter(line: Line, query: String) : Boolean{
        return line.name.toLowerCase().clean().contains(query.toLowerCase().clean())
                || line.code.toLowerCase().startsWith(query.toLowerCase())
    }

    fun getLines(refresh :Boolean = false){

        if(!isRequested || refresh) {

            isRequested = true
            loadingEvent.value = true

            getLinesUseCase.execute(object : DefaultSingleObserver<List<Line>>() {

                override fun onError(e: Throwable) {
                    loadingEvent.value = false
                }

                override fun onSuccess(t: List<Line>) {
                    loadingEvent.value = false

                    favoriteLines.value = t.filter { line->line.favorite }
                    if(!refresh){
                        hasFavorite.value = favoriteLines.value!= null && favoriteLines.value!!.isNotEmpty()
                    }
                    lines.value = t
                }
            }, Unit)
        }
    }

    fun favoriteLine(line: Line){
        line.favorite = !line.favorite
        updateLineUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                getLines(true)
            }
        },UpdateLine.Params(line))
    }

    override fun onCleared() {
        super.onCleared()
        getLinesUseCase.dispose()
        updateLineUseCase.dispose()
    }
}
package br.com.disapps.meucartaotransporte.ui.lines

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.lines.GetLines
import br.com.disapps.domain.interactor.lines.UpdateLine
import br.com.disapps.domain.model.Line
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.model.mappers.toLineBO
import br.com.disapps.meucartaotransporte.model.mappers.toLineVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class LinesViewModel(private val getLinesUseCase: GetLines,
                     private val updateLineUseCase: UpdateLine) : BaseViewModel(){

    val hasFavorite = MutableLiveData<Boolean>()
    val isUpdatedLines = MutableLiveData<Boolean>()
    val isUpdatedFavorites = MutableLiveData<Boolean>()
    val lines = ArrayList<LineVO>()
    val favoriteLines = ArrayList<LineVO>()
    val linesFiltered = ArrayList<LineVO>()

    fun filterLines(query:String){
        linesFiltered.clear()
        linesFiltered.addAll(lines.filter { filter(it, query) })
    }

    private fun filter(line: LineVO, query: String) : Boolean{
        return line.searchableName.contains(query) || line.code.toLowerCase().startsWith(query)
    }

    fun getLines(refresh :Boolean = false){

        if(!isRequested || refresh) {
            isRequested = true

            getLinesUseCase.execute(object : DefaultSingleObserver<List<Line>>() {

                override fun onSuccess(t: List<Line>) {
                    favoriteLines.clear()
                    lines.clear()

                    lines.addAll(t.toLineVO())
                    favoriteLines.addAll(t.toLineVO().filter { line->line.favorite })

                    if(!refresh){
                        hasFavorite.value = favoriteLines.isNotEmpty()
                    }

                    isUpdatedLines.value = true
                    isUpdatedFavorites.value = true

                }
            }, Unit)
        }
    }

    fun favoriteLine(line: LineVO){
        line.favorite = !line.favorite
        updateLineUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                getLines(true)
            }
        },UpdateLine.Params(line.toLineBO()))
    }

    override fun onCleared() {
        super.onCleared()
        getLinesUseCase.dispose()
        updateLineUseCase.dispose()
    }
}
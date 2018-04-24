package br.com.disapps.meucartaotransporte.ui.lines

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.UseCaseCompletableCallback
import br.com.disapps.domain.interactor.base.UseCaseCallback
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

    fun getLines(refresh : Boolean = false){
        getLinesUseCase.execute(object : UseCaseCallback<List<Line>>() {
            override fun onSuccess(t: List<Line>) {
                updateLines(t.toLineVO())
                updateFavorites(t.toLineVO().filter { line->line.favorite })
                if(!isRequested && !refresh){
                    isRequested = true
                    hasFavorite.value = favoriteLines.isNotEmpty()
                }
            }
        }, Unit)
    }

    fun favoriteLine(line: LineVO){
        line.favorite = !line.favorite
        updateLines(lines.map{
            if(it.code == line.code){
                it.favorite = line.favorite
            }
            it
        })
        updateFavorites(lines.filter { l->l.favorite })
        updateLineUseCase.execute(object : UseCaseCompletableCallback(){},UpdateLine.Params(line.toLineBO()))
    }

    private fun updateLines(listLines : List<LineVO>){
        lines.clear()
        lines.addAll(listLines)
        isUpdatedLines.value = true
    }

    private fun updateFavorites(lines : List<LineVO>){
        favoriteLines.clear()
        favoriteLines.addAll(lines)
        isUpdatedFavorites.value = true
    }

    override fun onCleared() {
        super.onCleared()
        getLinesUseCase.dispose()
        updateLineUseCase.dispose()
    }
}
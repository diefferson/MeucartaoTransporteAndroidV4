package br.com.disapps.meucartaotransporte.ui.lines

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.onFailure
import br.com.disapps.domain.interactor.base.onSuccess
import br.com.disapps.domain.interactor.lines.GetLines
import br.com.disapps.domain.interactor.lines.UpdateLine
import br.com.disapps.meucartaotransporte.model.mappers.toLineBO
import br.com.disapps.meucartaotransporte.model.mappers.toLineVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.ui.lines.LinesListAdapter.ListItem as LineItem

/**
 * Created by dnso on 12/03/2018.
 */
class LinesViewModel(private val getLinesUseCase: GetLines,
                     private val updateLineUseCase: UpdateLine) : BaseViewModel(){

    val hasFavorite = MutableLiveData<Boolean>()
    val isUpdatedLines = MutableLiveData<Boolean>()
    val isUpdatedFavorites = MutableLiveData<Boolean>()

    val lines = ArrayList<LineItem>()
    val favoriteLines = ArrayList<LineItem>()
    val linesFiltered = ArrayList<LineItem>()

    fun filterLines(query:String){
        linesFiltered.clear()
        linesFiltered.addAll( LinesListAdapter.objectToItem( lines.filter { filter(it, query) }.map { it.line } ) )
        isUpdatedLines.value = true
    }

    private fun filter(lineItem: LineItem, query: String) : Boolean{
        return lineItem.line.searchableName.contains(query) || lineItem.line.code.toLowerCase().startsWith(query)
    }

    fun getLines(){
        if(!isRequested){
            isRequested = true
            loadingEvent.value = true
            getLinesUseCase(this).onFailure {
                loadingEvent.value = false
                isUpdatedFavorites.value = true
                isUpdatedLines.value = true
            }.onSuccess{
                loadingEvent.value = false
                updateLines(LinesListAdapter.objectToItem(it.toLineVO()))
                updateFavorites(LinesListAdapter.objectToItem( it.toLineVO().filter { it.favorite }) )
                hasFavorite.value = favoriteLines.isNotEmpty()
            }
        }
    }

    fun updateFavoriteLine(lineCode : String, isFavorite :Boolean){

        updateLines( lines.map{
            if(it.line.code == lineCode){
                it.line.favorite = isFavorite
            }
            it
        })

        updateFavorites( lines.filter { it.line.favorite } )
    }

    fun favoriteLine(lineItem: LineItem){
        lineItem.line.favorite = !lineItem.line.favorite
        updateLineUseCase(this,UpdateLine.Params(lineItem.line.toLineBO()))
        updateFavoriteLine(lineItem.line.code, lineItem.line.favorite)
    }

    private fun updateLines(listLines : List<LineItem>){
        lines.clear()
        lines.addAll(listLines)
        isUpdatedLines.value = true
    }

    private fun updateFavorites(lines : List<LineItem>){
        favoriteLines.clear()
        favoriteLines.addAll(lines)
        isUpdatedFavorites.value = true
    }
}
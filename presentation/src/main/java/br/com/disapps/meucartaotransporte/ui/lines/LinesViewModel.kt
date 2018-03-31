package br.com.disapps.meucartaotransporte.ui.lines

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.DefaultObserver
import br.com.disapps.domain.interactor.lines.GetLines
import br.com.disapps.domain.interactor.lines.UpdateLine
import br.com.disapps.domain.model.Line
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class LinesViewModel(private val getLinesUseCase: GetLines,
                     private val updateLineUseCase: UpdateLine) : BaseViewModel(){

    private var isRequested  = false
    val lines = MutableLiveData<List<Line>>()
    val favoriteLines = MutableLiveData<List<Line>>()
    val hasFavorite = MutableLiveData<Boolean>()

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
        updateLineUseCase.execute(object : DefaultObserver<Boolean>(){
            override fun onNext(t: Boolean) {
                getLines(true)
            }
        }, UpdateLine.Params(line))
    }
}
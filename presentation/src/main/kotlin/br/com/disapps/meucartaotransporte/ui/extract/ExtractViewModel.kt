package br.com.disapps.meucartaotransporte.ui.extract

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.DefaultObserver
import br.com.disapps.domain.interactor.cards.GetExtract
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.meucartaotransporte.model.KnownError
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel


class ExtractViewModel(val getExtractUseCase: GetExtract) : BaseViewModel(){

    private var isRequested  = false
    val extract = MutableLiveData<List<Extract>>()

    fun getExtract(){
        if(!isRequested){
            isRequested = true
            loadingEvent.value = true
            getExtractUseCase.execute(object : DefaultObserver<List<Extract>>() {

                override fun onError(exception: Throwable) {
                    loadingEvent.value = false
                    errorEvent.value = KnownError("Erro!")
                }

                override fun onNext(t: List<Extract>) {
                    loadingEvent.value = false
                    extract.value = t
                }
            }, GetExtract.Params.forCard(Card(code = "2909840", cpf = "09695910980")))
        }
    }
}
package br.com.disapps.meucartaotransporte.ui.main

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import br.com.disapps.domain.interactor.preferences.GetInitialScreen
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.iab.IabHelper
import br.com.disapps.meucartaotransporte.util.iab.IabResult
import br.com.disapps.meucartaotransporte.util.iab.Inventory
import br.com.disapps.meucartaotransporte.util.iab.Purchase

/**
 * Created by dnso on 09/03/2018.
 */
class MainViewModel(private val getInitialScreenUseCase: GetInitialScreen) : BaseViewModel(){

    val searchText = MutableLiveData<String>()
    val onSearchAction = MutableLiveData<Boolean>()
    var isTabsVisible = true
    val initialScreen  = MutableLiveData<Int>()
    var iabHelper: IabHelper? = null
    var resultInap = MutableLiveData<String>()

    fun getInitialScreen(){
        initialScreen.value = 0
        getInitialScreenUseCase.execute(Unit){
            initialScreen.value = if(InitialScreen.CARDS.toString() == it){
                0
            }else{
                1
            }
        }
    }

    fun launchPurchaseFlow(activity:Activity){
        try {
            iabHelper?.launchPurchaseFlow(activity, SKU_PRO,RC_REQUEST,mPurchaseFinishedListener, "")
        } catch (e: IabHelper.IabAsyncInProgressException) {
            resultInap.value = "Erro ao iniciar o fluxo de compras. Outra operação assíncrona em andamento."
        }
    }

    fun queryInventoryAsync(){
        try {
            iabHelper?.queryInventoryAsync(mGotInventoryListener)
        } catch (e: IabHelper.IabAsyncInProgressException) {
            resultInap.value = "Erro ao consultar o inventário. Outra operação assíncrona em andamento."
        }
    }

    //Ouvinte que é chamado quando terminamos de consultar os itens e assinaturas que possuímos
    private var mGotInventoryListener: IabHelper.QueryInventoryFinishedListener = object : IabHelper.QueryInventoryFinishedListener {
        override fun onQueryInventoryFinished(result: IabResult, inventory: Inventory?) {
            if (result.isFailure) {
                resultInap.value = "Falha ao consultar inventário: $result"
                return
            }

            val premiumPurchase = inventory?.getPurchase(SKU_PRO)
        }
    }


    //Chamada de retorno quando a compra estiver concluída
    private var mPurchaseFinishedListener: IabHelper.OnIabPurchaseFinishedListener = object : IabHelper.OnIabPurchaseFinishedListener {
        override fun onIabPurchaseFinished(result: IabResult, purchase: Purchase?) {

            if (result.isFailure) {
                resultInap.value = "Erro ao comprar: $result"
                return
            }

            if (!verifyDeveloperPayload(purchase)) {
                resultInap.value = "Erro ao comprar. Falha na verificação de autenticidade."
                return
            }

            if (purchase!!.sku == SKU_PRO) {
                resultInap.value = "Obrigado por assinar a versão PRO"
                iabHelper?.flagEndAsync()
            }
        }
    }

    /** Verifies the developer payload of a purchase.  */
    internal fun verifyDeveloperPayload(p: Purchase?): Boolean {
        val payload = p?.developerPayload
        return true
    }

    override fun onCleared() {
        super.onCleared()
        getInitialScreenUseCase.dispose()
    }

    companion object {
        internal const val SKU_PRO = "remove_ads"
        internal const val RC_REQUEST = 10001
    }
}
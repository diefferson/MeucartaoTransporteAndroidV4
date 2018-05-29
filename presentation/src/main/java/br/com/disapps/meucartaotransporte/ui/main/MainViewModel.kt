package br.com.disapps.meucartaotransporte.ui.main

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.preferences.GetInitialScreen
import br.com.disapps.domain.interactor.preferences.GetIsPro
import br.com.disapps.domain.interactor.preferences.SetIsPro
import br.com.disapps.domain.model.InitialScreen
import br.com.disapps.meucartaotransporte.model.InAppBillingStatus
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.iab.IabHelper
import br.com.disapps.meucartaotransporte.util.iab.IabResult
import br.com.disapps.meucartaotransporte.util.iab.Inventory
import br.com.disapps.meucartaotransporte.util.iab.Purchase
import com.appodeal.ads.Appodeal

/**
 * Created by dnso on 09/03/2018.
 */
class MainViewModel(private val getInitialScreenUseCase: GetInitialScreen,
                    private val getIsProUseCase: GetIsPro,
                    private val setIsProUseCase: SetIsPro) : BaseViewModel(){

    val initialScreen  = MutableLiveData<Int>()
    val searchText = MutableLiveData<String>()
    val onSearchAction = MutableLiveData<Boolean>()
    val isPro = MutableLiveData<Boolean>()
    val resultInAppBilling = MutableLiveData<InAppBillingStatus>()
    var isTabsVisible = true
    var iabHelper: IabHelper? = null

    init {
        getIsProUseCase.execute(Unit){
            isPro.value = it
        }
    }

    fun getInitialScreen(){
        initialScreen.value = 0
        getInitialScreenUseCase.execute(Unit){
            initialScreen.value = if(InitialScreen.CARDS.toString() == it){0}else{1}
        }
    }

    fun launchPurchaseFlow(activity:Activity){
        try {
            iabHelper?.launchPurchaseFlow(activity, SKU_PRO,RC_REQUEST,mPurchaseFinishedListener, "")
        } catch (e: IabHelper.IabAsyncInProgressException) {
            resultInAppBilling.value = InAppBillingStatus.ERROR_INIT
        }
    }

    fun queryInventoryAsync(){
        try {
            iabHelper?.queryInventoryAsync(mGotInventoryListener)
        } catch (e: IabHelper.IabAsyncInProgressException) {
            resultInAppBilling.value = InAppBillingStatus.ERROR_QUERY
        }
    }

    private var mGotInventoryListener: IabHelper.QueryInventoryFinishedListener = object : IabHelper.QueryInventoryFinishedListener {
        override fun onQueryInventoryFinished(result: IabResult, inventory: Inventory?) {
            if (result.isFailure) {
                resultInAppBilling.value = InAppBillingStatus.ERROR_QUERY_INVENTORY
                return
            }

            val premiumPurchase = inventory?.getPurchase(SKU_PRO)
            setIsProUseCase.execute(SetIsPro.Params( premiumPurchase != null))
            isPro.value = premiumPurchase != null
        }
    }

    private var mPurchaseFinishedListener: IabHelper.OnIabPurchaseFinishedListener = object : IabHelper.OnIabPurchaseFinishedListener {
        override fun onIabPurchaseFinished(result: IabResult, purchase: Purchase?) {

            if (result.isFailure) {
                return
            }

            if (purchase!!.sku == SKU_PRO) {
                resultInAppBilling.value = InAppBillingStatus.SUCCESS
                iabHelper?.flagEndAsync()
                Appodeal.setCustomRule(MainViewModel.SKU_PRO, true)
                setIsProUseCase.execute(SetIsPro.Params(true))
                isPro.value = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getInitialScreenUseCase.dispose()
        getIsProUseCase.dispose()
        setIsProUseCase.dispose()
    }

    companion object {
        internal const val SKU_PRO = "remove_ads"
        internal const val RC_REQUEST = 10001
    }
}
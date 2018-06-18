package br.com.disapps.meucartaotransporte.widgets.cardBalance

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import br.com.disapps.data.api.RestClient
import br.com.disapps.data.dataSource.cloud.CloudCardsDataSource
import br.com.disapps.data.dataSource.local.LocalCardsDataSource
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.data.entity.mappers.toCardBO
import br.com.disapps.data.entity.mappers.toCardDTO
import br.com.disapps.domain.interactor.cards.GetCards
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.mappers.toCardBO
import br.com.disapps.meucartaotransporte.model.mappers.toCardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import io.realm.Realm

class CardBalanceWidgetViewModel(private val getCardsUseCase: GetCards) : BaseViewModel(){

    val cards = MutableLiveData<List<CardVO>>()

    fun getCards(){
        loadingEvent.value = true
        getCardsUseCase.execute(Unit, onError = {
            loadingEvent.value = false
            cards.value = ArrayList()
        }){
            loadingEvent.value = false
            cards.value = it.toCardVO()
        }
    }

    override fun onCleared() {
        super.onCleared()
        getCardsUseCase.dispose()
    }

    companion object {

        internal fun loadCardInitialData(context: Context, appWidgetId: Int,prefsName :String, prefKey:String): CardVO? {
            return getCard(loadCodeCard(context, appWidgetId,prefsName, prefKey))
        }


        internal suspend fun loadCardData(context: Context, appWidgetId: Int,prefsName :String, prefKey:String): CardVO? {
            return try {
                updateCard(loadCodeCard(context, appWidgetId,prefsName, prefKey))
            }catch (e:Exception){
                null
            }
        }

        // Write the prefix to the SharedPreferences object for this widget
        internal fun saveCodeCard(context: Context, appWidgetId: Int, code: String,prefsName :String, prefKey:String) {
            val prefs = context.getSharedPreferences(prefsName, 0).edit()
            prefs.putString(prefKey + appWidgetId, code)
            prefs.apply()
        }

        private fun loadCodeCard(context: Context, appWidgetId: Int,prefsName :String, prefKey:String): String {
            val prefs = context.getSharedPreferences(prefsName, 0)
            val cardCode = prefs.getString(prefKey + appWidgetId, null)
            return cardCode ?: ""
        }

        internal fun deleteCodeCard(context: Context, appWidgetId: Int, prefsName :String, prefKey:String) {
            val prefs = context.getSharedPreferences(prefsName, 0).edit()
            prefs.remove(prefKey + appWidgetId)
            prefs.apply()
        }

        private suspend fun updateCard(code: String): CardVO? {
            val cards = CloudCardsDataSource(RestClient().api)
            val card = getCard(code)
            if(card!= null){
                val request = RequestCartao().apply {
                    codigo = card.code
                    cpf = card.cpf
                    tipoConsulta = "saldo"
                }

                val cardVo = cards.card(request)!!.toCardBO().toCardVO().apply { name = card.name }
                updateCardDB(cardVo)
                return cardVo
            }
            return null
        }

        private fun updateCardDB(card: CardVO) {
            val completeCard = getCard(card.code)!!.toCardBO().toCardDTO()
            completeCard.apply {
                data_saldo = card.balanceDate
                saldo = card.balance
            }

            val realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(completeCard)
            realm.commitTransaction()
            realm.close()
        }

        private fun getCard(code: String) :CardVO?{

            val realm = Realm.getDefaultInstance()
            val card = realm.copyFromRealm(realm.where(LocalCardsDataSource.CLAZZ)
                    .equalTo(LocalCardsDataSource.CODE, code)
                    .findAll())
            realm.close()
            if(card.isNotEmpty()){
                return card[0].toCardBO().toCardVO()
            }
            return null
        }
    }
}
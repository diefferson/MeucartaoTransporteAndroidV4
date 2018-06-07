package br.com.disapps.meucartaotransporte.widgets.cardBalanceBlack

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.data.api.RestClient
import br.com.disapps.data.dataSource.cloud.CloudCardsDataSource
import br.com.disapps.data.dataSource.local.LocalCardsDataSource
import br.com.disapps.data.entity.Cartao
import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.data.entity.mappers.toCardBO
import br.com.disapps.data.entity.mappers.toCardDTO
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.mappers.toCardBO
import br.com.disapps.meucartaotransporte.model.mappers.toCardVO
import br.com.disapps.meucartaotransporte.util.getEmptyView
import br.com.disapps.meucartaotransporte.util.getLoadingView
import br.com.disapps.meucartaotransporte.widgets.cardBalanceWhite.CardBalanceWidgetConfigureViewModel
import br.com.disapps.meucartaotransporte.widgets.cardBalanceWhite.ConfigureCardsListAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_my_cards.*
import org.koin.android.architecture.ext.viewModel

/**
 * The configuration screen for the [CardBalanceWidgetBlack] AppWidget.
 */
class CardBalanceWidgetBlackConfigureActivity : AppCompatActivity() {

    private var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    val viewModel by viewModel<CardBalanceWidgetConfigureViewModel>()

    private val adapter: ConfigureCardsListAdapter by lazy {
        ConfigureCardsListAdapter(ArrayList()).apply {
            setOnItemClickListener { adapter, _, position ->

                saveCodeCard(this@CardBalanceWidgetBlackConfigureActivity, mAppWidgetId, (adapter.data[position] as CardVO).code)

                val appWidgetManager = AppWidgetManager.getInstance(this@CardBalanceWidgetBlackConfigureActivity)
                CardBalanceWidgetBlack.createAppWidget(this@CardBalanceWidgetBlackConfigureActivity, appWidgetManager, mAppWidgetId)

                // Certifique-se de que devolvemos o appWidgetId original
                val resultValue = Intent()
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
                setResult(Activity.RESULT_OK, resultValue)
                finish()
            }
        }
    }


    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        // Defina o resultado para CANCELLED. Isso fará com que o host do widget cancele
        // fora do posicionamento do widget se o usuário pressionar o botão Voltar.
        setResult(Activity.RESULT_CANCELED)

        setContentView(R.layout.card_balance_widget_configure)
        title = getString(R.string.select_a_card)

//        // Encontre o id do widget da intenção.
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        // Se essa atividade foi iniciada com uma intenção sem um ID de widget do aplicativo, termine com um erro.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        initRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCards()
    }

    private fun initRecyclerView() {
        cards_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CardBalanceWidgetBlackConfigureActivity.adapter
        }
    }

    private fun observeViewModel(){
        viewModel.cards.observe(this, Observer {
            if(it!= null && it.isNotEmpty()){
                adapter.setNewData(it)
                hideErrorView()
            }else{
                showErrorView(getEmptyView(getString(R.string.no_cards_registred)))
            }
        })
    }

    fun setupLoading() {
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it!= null && it ){
                showErrorView(getLoadingView())
            }
        })
    }

    private fun showErrorView(view :View?){
        error_view.removeAllViews()
        error_view?.addView(view)
        error_view.visibility = View.VISIBLE
    }

    private fun hideErrorView(){
        error_view.removeAllViews()
        error_view.visibility = View.GONE
    }

    companion object {

        private const val PREFS_NAME = "br.com.disapps.meucartaotransporte.widgets.cardBalance.CardBalanceWidgetBlack"
        private const val PREF_PREFIX_KEY = "appwidget_"

        internal fun loadCardInitialData(context: Context, appWidgetId: Int): CardVO? {
            return getCard(loadCodeCard(context, appWidgetId))
        }


        internal suspend fun loadCardData(context: Context, appWidgetId: Int): CardVO? {
            return updateCard(loadCodeCard(context, appWidgetId))
        }

        // Write the prefix to the SharedPreferences object for this widget
        internal fun saveCodeCard(context: Context, appWidgetId: Int, code: String) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.putString(PREF_PREFIX_KEY + appWidgetId, code)
            prefs.apply()
        }

        // Read the prefix from the SharedPreferences object for this widget.
        // If there is no preference saved, get the default from a resource
        private fun loadCodeCard(context: Context, appWidgetId: Int): String {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null)
            return titleValue ?: context.getString(R.string.appwidget_text)
        }

        internal fun deleteCodeCard(context: Context, appWidgetId: Int) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.remove(PREF_PREFIX_KEY + appWidgetId)
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


package br.com.disapps.meucartaotransporte.widgets.cardBalance.cardBalanceWhite

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.util.getEmptyView
import br.com.disapps.meucartaotransporte.util.getLoadingView
import br.com.disapps.meucartaotransporte.widgets.cardBalance.CardBalanceWidgetViewModel
import br.com.disapps.meucartaotransporte.widgets.cardBalance.ConfigureCardsListAdapter
import kotlinx.android.synthetic.main.fragment_recycler.*
import org.koin.android.architecture.ext.viewModel

/**
 * The configuration screen for the [CardBalanceWidgetWhite] AppWidget.
 */
class CardBalanceWidgetWhiteConfigureActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "br.com.disapps.meucartaotransporte.widgets.cardBalance.CardBalanceWidgetWhite"
        const val PREF_PREFIX_KEY = "appwidgetCardWhite_"
    }

    private var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private val viewModel by viewModel<CardBalanceWidgetViewModel>()

    private val adapter: ConfigureCardsListAdapter by lazy {
        ConfigureCardsListAdapter(ArrayList()).apply {
            setOnItemClickListener { adapter, _, position ->

                CardBalanceWidgetViewModel.saveCodeCard(this@CardBalanceWidgetWhiteConfigureActivity,
                        mAppWidgetId,
                        (adapter.data[position] as CardVO).code,
                        PREFS_NAME,
                        PREF_PREFIX_KEY)

                val appWidgetManager = AppWidgetManager.getInstance(this@CardBalanceWidgetWhiteConfigureActivity)
                CardBalanceWidgetWhite.createAppWidget(this@CardBalanceWidgetWhiteConfigureActivity, appWidgetManager, mAppWidgetId)

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

        setResult(Activity.RESULT_CANCELED)

        setContentView(R.layout.fragment_recycler)
        title = getString(R.string.select_a_card)

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
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CardBalanceWidgetWhiteConfigureActivity.adapter
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

    private fun hideErrorView() {
        error_view.removeAllViews()
        error_view.visibility = View.GONE
    }
}


package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.*
import com.appodeal.ads.Appodeal
import kotlinx.android.synthetic.main.activity_balance.*
import org.koin.android.architecture.ext.viewModel

class BalanceActivity : BaseActivity() {

    override val viewModel by viewModel<BalanceViewModel>()
    override val activityLayout = R.layout.activity_balance
    private val adapter: BalanceListAdapter by lazy { BalanceListAdapter(ArrayList(), this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        observeViewModel()
        Appodeal.show(this, Appodeal.INTERSTITIAL)
    }

    override fun recreate() {
        isRecreated = true
        super.recreate()
    }

    override fun onResume() {
        super.onResume()
        val card = intent.getSerializableExtra(CARD) as CardVO

        if(validateConnection()){
            viewModel.getCard(card.code, card.cpf, isRecreated)
            isRecreated = false
        }else{
            adapter.emptyView = getOfflineView()
        }
    }

    private fun initRecyclerView(){
        balance_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@BalanceActivity.adapter
        }
    }

    private fun observeViewModel(){
        viewModel.card.observe(this, Observer {
            adapter.apply {
                if(it!= null){
                    setNewData(BalanceListAdapter.objectToItem(arrayListOf(it)))
                }
                emptyView = getEmptyView(getString(R.string.no_results))
            }
        })
    }

    override fun setupError() {
        viewModel.getErrorObservable().observe(this, Observer {error ->
            error?.let {
                adapter.emptyView = getErrorView(it)
            }
        })
    }

    override fun setupLoading() {
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it!= null && it ){
                adapter.emptyView = getLoadingView()
            }
        })
    }

    companion object {
        var isRecreated = false
        private const val CARD = "card"
        fun launch(context: Context,card : CardVO){
            context.startActivity(Intent(context, BalanceActivity::class.java).apply {
                putExtra(CARD, card)
            })
        }
    }
}
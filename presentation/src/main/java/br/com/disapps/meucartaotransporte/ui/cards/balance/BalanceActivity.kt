package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.inflateView
import br.com.disapps.meucartaotransporte.util.getAdViewContentStream
import kotlinx.android.synthetic.main.activity_balance.*
import org.koin.android.architecture.ext.viewModel

class BalanceActivity : BaseActivity() {

    override val viewModel by viewModel<BalanceViewModel>()

    override val activityLayout = R.layout.activity_balance

    private val adapter: BalanceListAdapter by lazy {
        BalanceListAdapter(ArrayList()).apply {
            emptyView = inflateView(R.layout.loading_view, balance_recycler )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        val card = intent.getSerializableExtra(CARD) as CardVO
        viewModel.getCard(card.code, card.cpf)
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
                setNewData(arrayListOf(it))
                setAdapterViews()
            }
        })

        viewModel.onError.observe(this, Observer {
            if(it!= null && it){
                adapter.emptyView = inflateView(R.layout.empty_view, balance_recycler )
            }
        })
    }

    private fun setAdapterViews(){
        try {
            adapter.apply {
                emptyView = inflateView(R.layout.empty_view, balance_recycler)
                setFooterView(getAdViewContentStream())
            }
        } catch(e : Exception){}
    }

    companion object {
        private const val CARD = "card"
        fun launch(context: Context,card : CardVO){
            context.startActivity(Intent(context, BalanceActivity::class.java).apply {
                putExtra(CARD, card)
            })
        }
    }
}
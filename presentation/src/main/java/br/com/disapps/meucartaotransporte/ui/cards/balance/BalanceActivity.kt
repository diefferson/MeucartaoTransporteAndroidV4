package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.animation.Animator
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.inflateView
import br.com.disapps.meucartaotransporte.util.getAdViewContentStream
import br.com.disapps.meucartaotransporte.util.getErrorView
import br.com.disapps.meucartaotransporte.util.validateConnection
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_balance.*
import org.koin.android.architecture.ext.viewModel

class BalanceActivity : BaseActivity() {

    override val viewModel by viewModel<BalanceViewModel>()
    override val activityLayout = R.layout.activity_balance
    private val adapter: BalanceListAdapter by lazy { BalanceListAdapter(ArrayList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        observeViewModel()
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
            val view = inflateView(R.layout.offline_view,balance_recycler )
            val animation = view.findViewById<LottieAnimationView>(R.id.animation_view)
            animation.addAnimatorListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    view.findViewById<TextView>(R.id.offlineText).visibility = View.VISIBLE
                }
            })
            adapter.emptyView = view
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
                setNewData(arrayListOf(it))
                emptyView = inflateView(R.layout.empty_view, balance_recycler)
                setFooterView(getAdViewContentStream())
            }
        })
    }

    override fun setupError() {
        viewModel.getErrorObservable().observe(this, Observer {error ->
            error?.let {
                adapter.emptyView = getErrorView(it, balance_recycler)
            }
        })
    }

    override fun setupLoading() {
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it!= null && it ){
                adapter.emptyView = inflateView(R.layout.loading_view, balance_recycler)
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
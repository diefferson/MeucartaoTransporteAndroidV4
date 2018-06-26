package br.com.disapps.meucartaotransporte.ui.cards.extract

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.*
import kotlinx.android.synthetic.main.activity_extract.*
import org.koin.android.architecture.ext.viewModel

class ExtractActivity : BaseActivity(){

    override val viewModel by viewModel<ExtractViewModel>()
    override val activityLayout = R.layout.activity_extract
    private val adapter : ExtractListAdapter by lazy { ExtractListAdapter(ArrayList(), this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        observeViewModel()
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
    }

    override fun recreate() {
        isRecreated = true
        super.recreate()
    }

    override fun onResume() {
        super.onResume()
        val card = intent.getSerializableExtra(CARD) as CardVO

        if(validateConnection()){
            viewModel.getExtract(card.code, card.cpf, isRecreated)
            isRecreated = false
        }else{
            adapter.emptyView = getOfflineView()
        }
    }

    private fun initRecyclerView() {
        extract_recycler.apply {
            layoutManager = LinearLayoutManager(this@ExtractActivity)
            adapter = this@ExtractActivity.adapter
        }
    }

    private fun observeViewModel(){
        viewModel.extract.observe(this, Observer {
            adapter.apply {
                setNewData(ExtractListAdapter.objectToItem(it))
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
            context.startActivity( Intent(context, ExtractActivity::class.java).apply {
                putExtra(CARD, card)
            })
        }
    }
}
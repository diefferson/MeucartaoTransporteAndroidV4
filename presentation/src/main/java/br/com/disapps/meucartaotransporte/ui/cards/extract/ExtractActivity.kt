package br.com.disapps.meucartaotransporte.ui.cards.extract

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.extensions.inflateView
import kotlinx.android.synthetic.main.activity_extract.*
import org.koin.android.architecture.ext.viewModel

class ExtractActivity : BaseActivity(){

    override val viewModel by viewModel<ExtractViewModel>()

    override val activityLayout = R.layout.activity_extract

    private val adapter : ExtractListAdapter by lazy {
        ExtractListAdapter(ArrayList()).apply {
            emptyView = inflateView(R.layout.loading_view, extract_recycler )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        extract_recycler.apply {
            layoutManager = LinearLayoutManager(this@ExtractActivity)
            adapter = this@ExtractActivity.adapter
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        val card = intent.getSerializableExtra(CARD) as CardVO
        viewModel.getExtract(card.code, card.cpf)
    }

    private fun observeViewModel(){
        viewModel.extract.observe(this, Observer {
            adapter.apply {
                emptyView = inflateView(R.layout.empty_view, extract_recycler )
                setNewData(it)
            }
        })
    }

    companion object {
        private const val CARD = "card"
        fun launch(context: Context,card : CardVO){
            context.startActivity( Intent(context, ExtractActivity::class.java).apply {
                putExtra(CARD, card)
            })
        }
    }
}
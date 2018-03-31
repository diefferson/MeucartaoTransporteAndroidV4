package br.com.disapps.meucartaotransporte.ui.cards.extract

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_extract.*
import org.koin.android.architecture.ext.viewModel

class ExtractActivity : BaseActivity(){

    override val viewModel by viewModel<ExtractViewModel>()

    override val activityLayout = R.layout.activity_extract

    private val adapter : ExtractListAdapter by lazy {
        ExtractListAdapter(ArrayList())
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
        val card = intent.extras.getSerializable(CARD) as CardVO
        viewModel.getExtract(card.code, card.cpf)
    }

    private fun observeViewModel(){
        viewModel.extract.observe(this, Observer {
            adapter.setNewData(it)
        })
    }

    companion object {
        private const val CARD = "card"
        fun launch(context: Context,card : CardVO){
            val intent = Intent(context, ExtractActivity::class.java)
            val params = Bundle()
            params.putSerializable(CARD, card)
            intent.putExtras(params)
            context.startActivity(intent)
        }
    }
}
package br.com.disapps.meucartaotransporte.ui.extract

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_extract.*
import org.koin.android.architecture.ext.getViewModel

class ExtractActivity : BaseActivity(){

    override val viewModel: ExtractViewModel
        get() = getViewModel()

    override val activityLayout: Int
        get() = R.layout.activity_extract

    private val adapter = ExtractListAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        extract_recycler.layoutManager = LinearLayoutManager(this)
        extract_recycler.adapter = adapter
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getExtract()
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
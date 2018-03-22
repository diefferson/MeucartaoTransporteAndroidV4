package br.com.disapps.meucartaotransporte.ui.myCards

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.balance.BalanceActivity
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.extract.ExtractActivity
import br.com.disapps.meucartaotransporte.util.extensions.toast
import kotlinx.android.synthetic.main.fragment_my_cards.*
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 14/03/2018.
 */
class MyCardsFragment : BaseFragment(){

    override val viewModel: MyCardsViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_my_cards

    private var adapter = CardsListAdapter(ArrayList())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cards_recycler.layoutManager = LinearLayoutManager(context)
        cards_recycler.adapter = adapter
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCards()
    }

    private fun observeViewModel(){
        viewModel.cards.observe(this, Observer {
            adapter.setNewData(it)
            adapter.setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.btn_card_balance -> BalanceActivity.launch(context!!, adapter.data[position] as CardVO)
                    R.id.btn_card_extract-> ExtractActivity.launch(context!!, adapter.data[position] as CardVO)
                    R.id.ic_delete_card-> confirmDeleteCard( adapter.data[position] as CardVO)
                }
            }
        })
    }

    private fun confirmDeleteCard(cardVO: CardVO){
        AlertDialog.Builder(context!!).apply {
            setTitle(getString(R.string.delete_card))
            setMessage(getString(R.string.msg_delete_card))
            setCancelable(false)
            setNegativeButton("Cancel", null)
            setPositiveButton("OK") { _, _ -> viewModel.deleteCard(cardVO) }
        }.create().show()
    }

    companion object {
        fun newInstance() = MyCardsFragment()
    }
}
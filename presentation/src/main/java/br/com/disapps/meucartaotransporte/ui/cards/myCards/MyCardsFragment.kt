package br.com.disapps.meucartaotransporte.ui.cards.myCards

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.cards.balance.BalanceActivity
import br.com.disapps.meucartaotransporte.ui.cards.extract.ExtractActivity
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.util.getEmptyView
import br.com.disapps.meucartaotransporte.util.getLoadingView
import kotlinx.android.synthetic.main.fragment_my_cards.*
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 14/03/2018.
 */
class MyCardsFragment : BaseFragment(){

    override val viewModel by viewModel<MyCardsViewModel>()

    override val fragmentLayout = R.layout.fragment_my_cards

    private val adapter: CardsListAdapter by lazy {
        CardsListAdapter(ArrayList(), activity!!).apply {
            setOnItemChildClickListener { _, view, position ->
                when(view.id){
                    R.id.btn_card_balance -> BalanceActivity.launch(context!!, getCard(position))
                    R.id.btn_card_extract-> ExtractActivity.launch(context!!, getCard(position))
                    R.id.ic_delete_card-> confirmDeleteCard(getCard(position))
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            adapter = this@MyCardsFragment.adapter
        }
    }

    private fun observeViewModel(){
        viewModel.cards.observe(this, Observer {
            adapter.apply {
                setNewData(CardsListAdapter.objectToItem(it))
                emptyView = activity.getEmptyView(getString(R.string.no_cards))
            }
        })
    }

    override fun setupLoading() {
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it!= null && it ){
                adapter.emptyView = activity?.getLoadingView()
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
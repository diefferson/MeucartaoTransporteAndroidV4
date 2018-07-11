package br.com.disapps.meucartaotransporte.ui.club.promotions

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.club.promotion.PromotionActivity
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_recycler.*
import org.koin.android.architecture.ext.viewModel

class PromotionsFragment : BaseFragment(){

    override val viewModel: PromotionsViewModel  by viewModel()
    override val fragmentLayout = R.layout.fragment_recycler
    override val fragmentTag = "PromotionsFragment"

    private val adapter : PromotionsListAdapter by lazy {
        PromotionsListAdapter(ArrayList()).apply {
            setOnItemClickListener { _, _, _ ->  PromotionActivity.launch(context!!)}
        }
    }

    companion object {
        fun newInstance() = PromotionsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPromotions()
    }

    fun observeViewModel() {
        viewModel.promotions.observe(this, Observer {
            adapter.setNewData(it)
        })
    }

    private fun initRecyclerView() {
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PromotionsFragment.adapter
        }
    }
}
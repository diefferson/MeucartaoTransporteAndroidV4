package br.com.disapps.meucartaotransporte.ui.club

import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.cards.CardsPageAdapter
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import kotlinx.android.synthetic.main.fragment_tabs.*
import org.koin.android.viewmodel.ext.android.viewModel


class ClubFragment :BaseFragment(){

    init {
        hasTabs = true
    }

    override val viewModel by viewModel<BaseViewModel>()
    override val fragmentLayout = R.layout.fragment_tabs
    override val fragmentTag = "ClubFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iAppActivityListener.setTitle(getString(R.string.bus_club))
        view_pager.adapter = ClubPageAdapter(childFragmentManager, context!!)
        iAppActivityListener.setupTabs(view_pager)
    }

    companion object {
        fun newInstance() = ClubFragment()
    }
}
package br.com.disapps.meucartaotransporte.ui.club.clubCard

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.viewModel

class ClubCardFragment :BaseFragment(){

    override val viewModel: ClubCardViewModel by viewModel()

    override val fragmentLayout = R.layout.fragment_recycler

    override val fragmentTag = "ClubCardFragment"

    companion object {
        fun newInstance() = ClubCardFragment()
    }
}
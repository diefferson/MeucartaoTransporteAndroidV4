package br.com.disapps.meucartaotransporte.base.ui.cards

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.base.ui.cards.registerCard.RegisterCardActivity
import br.com.disapps.meucartaotransporte.base.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.base.ui.common.BaseViewModel
import kotlinx.android.synthetic.main.fragment_cards.*
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 12/03/2018.
 */
class CardsFragment : BaseFragment(){

    init {
        hasTabs = true
    }

    override val viewModel by viewModel<BaseViewModel>()
    override val fragmentTag = "CardsFragment"
    override val fragmentLayout = R.layout.fragment_cards

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        iAppActivityListener.setTitle(getString(R.string.app_name))
        view_pager.adapter = CardsPageAdapter(childFragmentManager, context!!)
        iAppActivityListener.setupTabs(view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_cards, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when (id) {
            R.id.action_add_card -> {
                RegisterCardActivity.launch(context!!)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance() = CardsFragment()
    }

}
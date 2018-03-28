package br.com.disapps.meucartaotransporte.ui.cards

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.cards.registerCard.RegisterCardActivity
import kotlinx.android.synthetic.main.fragment_cards.*
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class CardsFragment : BaseFragment(){

    companion object {
        fun newInstance() = CardsFragment()
    }

    override val viewModel: CardsViewModel
        get() =  getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_cards

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        view_pager.adapter = CardsPageAdapter(childFragmentManager, context!!)
        tabs.setupWithViewPager(view_pager)
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

}
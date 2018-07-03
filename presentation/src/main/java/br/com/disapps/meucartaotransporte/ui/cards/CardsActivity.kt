package br.com.disapps.meucartaotransporte.ui.cards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.cards.quickFind.QuickFindFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragmentActivity
import br.com.disapps.meucartaotransporte.ui.custom.SearchAnimationToolbar
import br.com.disapps.meucartaotransporte.ui.main.MainViewModel
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar_tabs.*
import org.koin.android.architecture.ext.viewModel

class CardsActivity :BaseFragmentActivity(){

    override val container: FrameLayout by lazy { vContainer  }
    override val toolbar: SearchAnimationToolbar  by lazy { vToolbar }
    override val tabs: TabLayout  by lazy { vTabs }
    override val appBar: AppBarLayout by lazy { vAppBar }
    override val viewModel: MainViewModel by viewModel()
    override val activityLayout = R.layout.activity_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(QuickFindFragment.newInstance())
    }

    override fun getIsTabsVisible() = viewModel.isTabsVisible

    override fun setIsTabsVisible(visible: Boolean) {
        viewModel.isTabsVisible = visible
    }

    override fun setSearchQuery(query: String) {
        viewModel.searchText.value = query
    }

    override fun onSearchAction(isOpen: Boolean) {
        viewModel.onSearchAction.value = isOpen
    }

    companion object {
        fun launch(context: Context){
            context.startActivity( Intent(context, CardsActivity::class.java))
        }
    }
}
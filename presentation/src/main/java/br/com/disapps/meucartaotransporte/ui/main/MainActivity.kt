package br.com.disapps.meucartaotransporte.ui.main

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.cards.CardsFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragmentActivity
import br.com.disapps.meucartaotransporte.ui.custom.SearchAnimationToolbar
import br.com.disapps.meucartaotransporte.ui.lines.LinesFragment
import br.com.disapps.meucartaotransporte.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar_tabs.*
import org.koin.android.architecture.ext.viewModel

class MainActivity : BaseFragmentActivity(){

    override val viewModel by viewModel<MainViewModel>()
    override val activityLayout = R.layout.activity_main
    override val container: FrameLayout by lazy { vContainer  }
    override val toolbar: SearchAnimationToolbar by lazy { vToolbar }
    override val tabs: TabLayout by lazy { vTabs }
    override val appBar: AppBarLayout by lazy { vAppBar }
    private var fragmentSelected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        viewModel.getInitialScreen()
        viewModel.initialScreen.observe(this, Observer {
            setupActualFragment(savedInstanceState, it?:0)
        })
    }

    private fun setupActualFragment(savedInstanceState: Bundle?, initialFragment: Int){

        savedInstanceState?.let {
            fragmentSelected = it.getInt(FRAGMENT_SELECTED, 0)
        }?: run{
            fragmentSelected = initialFragment
        }

        navigation.selectedItemId = when(fragmentSelected){
            0 -> R.id.nav_cards
            1-> R.id.nav_lines
            2-> R.id.nav_settings
            else -> R.id.nav_cards
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(FRAGMENT_SELECTED, fragmentSelected)
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

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_cards -> {
                fragmentSelected  = 0
                replaceFragment( CardsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_lines -> {
                fragmentSelected  = 1
                replaceFragment(LinesFragment.newInstance() )
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_settings -> {
                fragmentSelected  = 2
                replaceFragment(SettingsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    companion object {
        private const val FRAGMENT_SELECTED = "fragmentSelected"
        fun launch(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}


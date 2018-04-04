package br.com.disapps.meucartaotransporte.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.cards.CardsFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
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

    override val initialFragment: BaseFragment by lazy { viewModel.actualFragment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun setSearchQuery(query: String) {
        viewModel.searchText.value = query
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_cards -> {
                viewModel.actualFragment = CardsFragment.newInstance()
                replaceFragment(viewModel.actualFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_lines -> {
                viewModel.actualFragment = LinesFragment.newInstance()
                replaceFragment(viewModel.actualFragment )
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_settings -> {
                viewModel.actualFragment = SettingsFragment.newInstance()
                replaceFragment(viewModel.actualFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    companion object {
        fun launch(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}


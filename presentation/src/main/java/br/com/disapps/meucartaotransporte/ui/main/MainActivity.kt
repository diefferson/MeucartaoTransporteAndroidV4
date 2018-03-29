package br.com.disapps.meucartaotransporte.ui.main

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.cards.CardsFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BaseFragmentActivity
import br.com.disapps.meucartaotransporte.ui.lines.LinesFragment
import br.com.disapps.meucartaotransporte.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar_tabs.*
import org.koin.android.architecture.ext.getViewModel

class MainActivity : BaseFragmentActivity(){

    override val viewModel : MainViewModel
        get() = getViewModel()

    override val activityLayout: Int
        get() = R.layout.activity_main

    override val container: FrameLayout
        get() = vContainer

    override val toolbar: Toolbar
        get() = vToolbar

    override val tabs: TabLayout
        get() = vTabs

    override val appBar: AppBarLayout
        get() = vAppBar

    override val initialFragment: BaseFragment
        get() = CardsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_cards -> {
                replaceFragment(CardsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_lines -> {
                replaceFragment(LinesFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_settings -> {
                replaceFragment(SettingsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun checkItemMenu(itemId: Int){
        navigation.menu.findItem(itemId).isChecked = true
    }

    init {
        initialFragmentItemId = R.id.nav_cards
    }
}

